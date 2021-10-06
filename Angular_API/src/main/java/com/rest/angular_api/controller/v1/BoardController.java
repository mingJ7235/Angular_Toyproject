package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.boards.Board;
import com.rest.angular_api.model.response.SingleResult;
import com.rest.angular_api.service.ResponseService;
import com.rest.angular_api.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping (value = "/vi/board")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회하는 api")
    @GetMapping (value = "/{boardName}")
    public SingleResult<Board> boardInfo (@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.findBoard(boardName));
    }



}
