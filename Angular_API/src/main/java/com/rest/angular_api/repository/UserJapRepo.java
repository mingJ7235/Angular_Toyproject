package com.rest.angular_api.repository;

import com.rest.angular_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJapRepo extends JpaRepository<User, Long> {

}
