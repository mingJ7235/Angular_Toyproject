package com.rest.angular_api.service.board;

import com.rest.angular_api.advice.exception.CResourceNotExistException;
import com.rest.angular_api.entity.boards.Board;
import com.rest.angular_api.entity.boards.Post;
import com.rest.angular_api.repository.BoardJpaRepo;
import com.rest.angular_api.repository.PostJpaRepo;
import com.rest.angular_api.repository.UserJpaRepo;
import javafx.scene.chart.BarChart;
import lombok.RequiredArgsConstructor;
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

}
