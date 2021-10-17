package com.rest.angular_api.service.reply;

import com.rest.angular_api.advice.exception.CResourceNotExistException;
import com.rest.angular_api.advice.exception.CUserExistException;
import com.rest.angular_api.entity.boards.Post;
import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.entity.reply.Reply;
import com.rest.angular_api.model.board.ParamsPost;
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

    public Reply saveReply (String email, Long postId, ParamsPost paramsPost) {
        User user = userJpaRepo.findByUid(email).orElseThrow(CUserExistException::new);
        Post post = postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new);
        Reply reply = Reply.builder()
                .replyContent(paramsPost.getContent())
                .level()
                .build();

    }

    private Reply getReply (Long replyId) {
        return replyJpaRepo.findById(replyId).orElseThrow(CResourceNotExistException::new);
    }

}
