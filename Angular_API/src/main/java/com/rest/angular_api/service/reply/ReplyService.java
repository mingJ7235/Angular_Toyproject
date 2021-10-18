package com.rest.angular_api.service.reply;

import com.rest.angular_api.advice.exception.CResourceNotExistException;
import com.rest.angular_api.advice.exception.CUserExistException;
import com.rest.angular_api.entity.boards.Post;
import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.entity.reply.Reply;
import com.rest.angular_api.model.board.ParamsPost;
import com.rest.angular_api.model.reply.ParamsReply;
import com.rest.angular_api.repository.PostJpaRepo;
import com.rest.angular_api.repository.ReplyJpaRepo;
import com.rest.angular_api.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final UserJpaRepo userJpaRepo;
    private final PostJpaRepo postJpaRepo;
    private final ReplyJpaRepo replyJpaRepo;

    public Reply saveReply (String email, Long postId, ParamsReply paramsReply) {
        User user = userJpaRepo.findByUid(email).orElseThrow(CUserExistException::new);
        Post post = postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new);
        if (paramsReply.getParentReplyId() == null) { // 부모 댓글일 경우
            Reply reply = Reply.builder()
                    .replyContent(paramsReply.getContent())
                    .level(1)
                    .isLive(true)
                    .user(user)
                    .post(post)
                    .parentReply(null)
                    .build();
            replyJpaRepo.save(reply);
            return reply;
        } else {
            Long parentReplyId = paramsReply.getParentReplyId();
            Reply parentReply = replyJpaRepo.findById(parentReplyId).orElseThrow(CResourceNotExistException::new);
            Reply reply = Reply.builder()
                    .replyContent(paramsReply.getContent())
                    .level(parentReply.getLevel() + 1)
                    .isLive(true)
                    .user(user)
                    .post(post)
                    .parentReply(parentReply)
                    .build();
            replyJpaRepo.save(reply);
            return reply;
        }

    }

    public Reply updateReply (String email, Long postId, ParamsReply paramsReply) {

        User user = userJpaRepo.findByUid(email).orElseThrow(CUserExistException::new);
        long findMsrl = postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new).getUser().getMsrl();


        if (user.getMsrl() == findMsrl) {

        }

        return Reply.builder()
                .build();
    }

    private Reply getReply (Long replyId) {
        return replyJpaRepo.findById(replyId).orElseThrow(CResourceNotExistException::new);
    }

}
