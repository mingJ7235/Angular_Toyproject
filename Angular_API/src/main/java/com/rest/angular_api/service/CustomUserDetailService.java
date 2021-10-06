package com.rest.angular_api.service;

import com.rest.angular_api.advice.exception.CUserNotFoundException;
import com.rest.angular_api.config.redis.redisConfig;
import com.rest.angular_api.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 빈번히 호출되는 Method Cache 처리
 * - 자주 호출되는 method에 대한 caching 처리가 중요
 * - CustomUserDetailService의 메소드는 회원정보를 빈번히 호출하기 때문에 caching을 적용하는것이 좋다.
 * - @Cacheable을 선언하면, 해당 메서드가 호출될 때 캐시가 없을 경우(최초불러질경우) DB에서 가져와 캐시를 생성하고 데이터를 반환한다.
 * - 캐시가 이미 있는 경우에는 당연히 DB를 거치지 않고 바로 캐시 데이터를 반환한다.
 */

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    /**
     * value : 저장시 key 값
     * key : 키 생성시 추가로 덧붙일 파라미터 정보 선언
     *       여기서는 메서드의 인자인 userPk를 선언한다.
     * unless = "#result == null" 은 메서드 결과가 null이 아닌경우에만 캐싱하도록 하는 옵션이다.
     */
    @Cacheable(value = redisConfig.CacheKey.USER, key = "#userPk", unless = "#result == null")
    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
    }

}
