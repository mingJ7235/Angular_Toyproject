package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.reply.Reply;
import com.rest.angular_api.model.reply.ParamsReply;
import com.rest.angular_api.model.response.SingleResult;
import com.rest.angular_api.service.ResponseService;
import com.rest.angular_api.service.board.BoardService;
import com.rest.angular_api.service.reply.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. Reply"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/reply")
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 등록", notes = "댓글을 등록한다.")
    @PostMapping ("/{postId}")
    public SingleResult<Reply> addReply (@PathVariable Long postId, @ModelAttribute ParamsReply paramsReply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(replyService.saveReply(uid, postId, paramsReply));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @PutMapping ("/{postId}/reply/{replyId}")
    public SingleResult<Reply> updateReply (
            @PathVariable Long postId,
            @PathVariable Long replyId,
            @ModelAttribute ParamsReply paramsReply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(replyService.updateReply(uid, postId, replyId, paramsReply));
    }

}
