package com.rest.angular_api.entity.boards;

import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.entity.util.CommonDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Post extends CommonDateEntity /*implements Serializable*/ {
    @Id
    @GeneratedValue
    private Long postId;

    @Column (nullable = false, length = 50)
    private String author;

    @Column (nullable = false, length = 100)
    private String title;

    @Column (length = 500)
    private String content;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "msrl")
    private User user;

    public Post(User user, Board board, String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
    }

    public Post setUpdate (String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        return this;
    }

}
