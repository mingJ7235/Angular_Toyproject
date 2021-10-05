package com.rest.angular_api.repository;

import com.rest.angular_api.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    //회원 가입시 가입한 이메일 조회 (uid가 security의 username값임)
    Optional<User> findByUid (String email);

    //uid 와 provider 정보로 social 회원 정보를 조회하는 메서드
    Optional<User> findByUidAndProvider (String uid, String provider);
}
