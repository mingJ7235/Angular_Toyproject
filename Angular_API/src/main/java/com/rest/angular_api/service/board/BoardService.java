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
import com.rest.angular_api.repository.UserJpaRepo;
import javafx.scene.chart.BarChart;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardJpaRepo boardJpaRepo;
    private final PostJpaRepo postJpaRepo;
    private final UserJpaRepo userJpaRepo;

    //게시판 이름으로 게시판 조회
    public Board findBoard (String boardName) {
        return Optional.ofNullable(boardJpaRepo.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
    }

    //게시판 이름으로 게시물 리스트 조회
    public List<Post> findPosts (String boardName) {
        return postJpaRepo.findByBoard(findBoard(boardName));
    }

    //게시물 아이디로 게시물 단건 조회.
    public Post getPost (Long postId) {
        return postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new);
    }

    //게시물 등록
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

    //게시물 수정
    public Post updatePost (long postId, String uid, ParamsPost paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getUid())) {
            throw new CNotOwnerException();
        }
        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return post;
    }

    //게시물 삭제
    public boolean deletePost (long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getUid())) {
            throw new CNotOwnerException();
        }
        postJpaRepo.delete(post);
        return true;
    }


}
