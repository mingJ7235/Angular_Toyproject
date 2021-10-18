package com.rest.angular_api.service.board;

import com.rest.angular_api.advice.exception.CNotOwnerException;
import com.rest.angular_api.advice.exception.CResourceNotExistException;
import com.rest.angular_api.advice.exception.CUserNotFoundException;
import com.rest.angular_api.entity.boards.Board;
import com.rest.angular_api.entity.boards.Post;
import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.model.board.ParamsPost;
import com.rest.angular_api.repository.BoardJpaRepo;
import com.rest.angular_api.repository.PostJpaRepo;
import com.rest.angular_api.repository.ReplyJpaRepo;
import com.rest.angular_api.repository.UserJpaRepo;
import com.rest.angular_api.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CRUD 메서드에 캐싱처리
 * - Spring에서는 캐싱을위해서 몇가지 annotation을 제공한다.
 *
 * - @Cacheable : 캐시가 존재하면 메서드를 실행하지 않고 캐시된 값이 반환된다.
 *                캐시가 존재하지 않으면 메서드가 실행되고 리턴되는 데이터가 캐시에 저장된다.
 * - @CachePut : 캐시에 데이터를 넣거나 수정시 사용된다. 메서드의 리턴값이 캐시에 없으면 저장하고 있을 경우 갱신한다.
 * - @CacheEvict : 캐시를 삭제한다.
 * - @Caching : 여러개의 캐시 annotation이 실행되어야할 때 사용된다.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardJpaRepo boardJpaRepo;
    private final PostJpaRepo postJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ReplyJpaRepo replyJpaRepo;
    //private final CacheService cacheService;

    public Board insertBoard(String boardName) {
        return boardJpaRepo.save(Board.builder().name(boardName).build());
    }

    //게시판 이름으로 게시판 조회
    //@Cacheable (value = redisConfig.CacheKey.BOARD, key = "#boardName", unless = "#result == null")
    public Board findBoard (String boardName) {
        return Optional.ofNullable(boardJpaRepo.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
    }

    //게시판 이름으로 게시물 리스트 조회
    //@Cacheable(value = redisConfig.CacheKey.POSTS, key = "#boardName", unless = "#result == null")
    public List<Post> findPosts (String boardName) {
        return postJpaRepo.findByBoard(findBoard(boardName));
    }

    //게시물 아이디로 게시물 단건 조회.
    public Post getPost (Long postId) {
        return postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new);
    }

    /**
     * @CacheEvict
     * 데이터 등록시 (writePost) 대부분 캐시 처리가 필요없다. 왜냐하면 캐시는 읽기 부하를 낮추기 위해서 사용하기 때문이다.
     * 하지만 writePost 메소드는 게시글 1건 등록할경우, 게시글 리스트 캐시를 초기화해야하므로 CacheEvict를 사용하여 캐시를 삭제해야한다. (게시물이 등록하면 달라지니까 초기화해야함)
     *
     */
    //@CacheEvict (value = redisConfig.CacheKey.POSTS, key = "#boardName")
    //게시물등록
    public Post writePost (String uid, String boardName, ParamsPost paramsPost) {
        Board board = boardJpaRepo.findByName(boardName);
        Post post = new Post(
                userJpaRepo.findByUid(uid).orElseThrow(CUserNotFoundException::new),
                board,
                paramsPost.getAuthor(),
                paramsPost.getTitle(),
                paramsPost.getContent());
        return postJpaRepo.save(post);
    }

    /**
     * 데이터 수정시 캐시처리
     * - 데이터 수정시에는 상황에 따라 캐시 처리에 두가지 선택이 존재한다.
     * 1. 기존캐시를 갱신하는 방법.
     *      단, 조건은 수정된 데이터의 캐시만 갱신하면 될 경우에 사용. 이경우는 CachePut을 사용한다.
     * 2. 캐시를 삭제하는 방법.
     *      연관된 캐시가 많아서 첫번째 방법으로 갱신이 힘든 경우.
     *      이런 경우에는 연관된 여러개의 캐시를 업데이트를 하는것보다, 삭제하는것이 더 편리하다.
     *      updatePost 메소드의 경우는 게시글과 게시글 리스트 2개의 캐시가 연관되어 있으므로 캐시를 삭제하는 것이 좋다.
     *      여러개의 캐시 annotation을 한번에 사용하면 @Caching을 사용하면된다.
     *      아래에서는 Service를 사용하였는데, 그이유는 CacheService.java에 기입해놓았다.
     */
    //게시물 수정
    public Post updatePost (long postId, String uid, ParamsPost paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();
        System.out.println();
        if (!uid.equals(user.getUid())) {
            throw new CNotOwnerException();
        }
        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        //cacheService.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return post;
    }

    /**
     * 데이터 삭제시 캐시 처리
     *
     * 데이터 삭제시에는 별다른 것 없이 캐시도 삭제하면딘다.
     * 단일건일 경우는 CacheEvict를 사용하면되고,
     * 여러개를 지우는것은 Caching 어노테이션을 사용하면되는데, 위의 수정과 마찬가지로 service를 사용하여 삭제한다.
     */
    //게시물 삭제
    public boolean deletePost (long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getUid())) {
            throw new CNotOwnerException();
        }
        postJpaRepo.delete(post);
        //cacheService.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return true;
    }


}
