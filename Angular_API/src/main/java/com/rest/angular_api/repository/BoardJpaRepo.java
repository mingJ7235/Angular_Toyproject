package com.rest.angular_api.repository;

import com.rest.angular_api.entity.boards.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepo extends JpaRepository<Board, Long> {

    Board findByName (String name);

}
