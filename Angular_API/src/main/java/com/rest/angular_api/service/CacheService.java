package com.rest.angular_api.service;

import com.rest.angular_api.config.redis.redisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * CacheService
 *
 * Spring에서 기본으로 Cache를 사용하게 되면 proxy모드로 동작을 하게 되는데, 다음과 같은 제약사항이 있다.
 * 1. public method에만 사용가능
 * 2. 같은 객체내의 메서드 호출시 annotation 메소드가 동작하지 않음.
 * 3. RTW (realtime weaving)로 처리되기 때문에 약간의 성능저하가 있음.
 *
 * 굳이 CacheService를 나누는 이유는 두가지다.
 * 1. 기존 방법으로는 메서드의 인자값으로 삭제할 cachekey를 조합할수 없어서 CacheService 만듦
 * 2. Proxy의 특성상 같은 객체내에서는 캐싱 처리된 메서드 호출시 동작하지 않기 때문에 만듦
 */

@Slf4j
@Service
public class CacheService {
    // @Caching annotation을 사용하여 여러개의 캐시를 한번에 삭제할 수 있다.
    @Caching(evict = {
            @CacheEvict (value = redisConfig.CacheKey.POST, key = "#postId"),
            @CacheEvict (value = redisConfig.CacheKey.POSTS, key = "#boardName")
    })
    public boolean deleteBoardCache (long postId, String boardName) {
        log.debug("deleteBoardCache - postId {}, boardName{}", postId, boardName);
        return true;
    }
}
