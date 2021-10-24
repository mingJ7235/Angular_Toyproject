package com.rest.angular_api.entity.reply;

import com.rest.angular_api.entity.boards.Post;
import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.entity.util.CommonDateEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter


@NoArgsConstructor
public class Reply extends CommonDateEntity {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column (length = 500)
    private String replyContent;

    private int level;

    private boolean isLive = true;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "msrl")
    private User user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "post_id")
    private Post post;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "parent_reply_id")
    private Reply parentReply;

    @OneToMany (mappedBy = "parentReply", cascade = CascadeType.ALL)
    private List<Reply> childrenReply = new ArrayList<>();

    @Builder
    public Reply (String replyContent, int level, boolean isLive, User user, Post post, Reply parentReply){
        this.replyContent = replyContent;
        this.level = level;
        this.isLive = isLive;
        this.user = user;
        this.post = post;
        this.parentReply = parentReply;
    }

}
