package com.rest.angular_api.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PreDestroy;

@Profile("local")// yml 파일에서 profile 구성. local환경에서만 사용할 것이라고 선언하는 것임
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}") //application-local.yml 에서 설정된 port
    private int redisPort;

    private RedisServer redisServer;

    public void redisServer () {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis () {
        if(redisServer != null) {
            redisServer.stop();
        }
    }

}
