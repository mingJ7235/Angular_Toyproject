package com.rest.angular_api.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long msrl;

    @Column (nullable = false, unique = true, length = 30)
    private String uid;

    @Column (nullable = false, length = 100)
    private String name;

    public User () {}

    public User (String uid, String name) {
        this.uid = uid;
        this.name = name;
    }


}
