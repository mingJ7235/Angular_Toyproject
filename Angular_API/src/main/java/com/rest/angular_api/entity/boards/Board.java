package com.rest.angular_api.entity.boards;

import com.rest.angular_api.entity.util.CommonDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Board extends CommonDateEntity /*implements Serializable*/ {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 100)
    private String name;
}
