package com.rest.angular_api.repository;

import com.rest.angular_api.entity.boards.Board;
import com.rest.angular_api.entity.boards.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post, Long> {
    List<Post> findByBoard (Board board);
}
