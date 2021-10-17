package com.rest.angular_api.entity.boards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.angular_api.entity.member.User;
import com.rest.angular_api.entity.reply.Reply;
import com.rest.angular_api.entity.util.CommonDateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends CommonDateEntity /*implements Serializable*/ {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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

    @OneToMany (mappedBy = "replyId")
    private List<Reply> reply;

    // 이걸 하지 않으면 Join table이 Json 결과에 표시되어 예외가 나온다.
    @JsonIgnore
    public Board getBoard () {
        return board;
    }

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
