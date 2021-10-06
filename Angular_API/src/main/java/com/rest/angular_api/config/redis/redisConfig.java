package com.rest.angular_api.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @EnableCaching : Redis 캐시사용 활성화
 * - 캐시 key별로 유효시간을 설정한다는것이 중요.
 * 또한, 캐시키에 유효시간을 설정하지 않은 경우에도 default유효시간으로 캐시가 생성되도록 세팅.
 * 이 설정을 하지 않으면 캐시가 설정될 때 유효시간 없이 설정되어 캐시가 지워지지 않으므로 메모리 부족 현상을 겪을 수 있다.
 */

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class redisConfig {
    @Bean (name = "cacheManager")
    public RedisCacheManager cacheManager (RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() //null value 는 cashing 안함
                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC)) // cash의 기본시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())); //redis 캐시 데이터 저장 방식을 StringSeriallizer로 지정

        //cache key 별 default 유효시간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.USER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.BOARD, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.BOARD_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POST, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.POSTS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.POST_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    public class CacheKey {
        public static final int DEFAULT_EXPIRE_SEC = 60; // 1 minutes
        public static final String USER = "user";
        public static final int USER_EXPIRE_SEC = 60 * 5; // 5 minutes
        public static final String BOARD = "board";
        public static final int BOARD_EXPIRE_SEC = 60 * 10; // 10 minutes
        public static final String POST = "post";
        public static final String POSTS = "posts";
        public static final int POST_EXPIRE_SEC = 60 * 5; // 5 minutes
    }

}
