package com.rest.angular_api.repository;

import com.rest.angular_api.entity.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyJpaRepo extends JpaRepository<Reply, Long> {

}
