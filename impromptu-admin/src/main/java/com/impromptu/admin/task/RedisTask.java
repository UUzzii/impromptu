package com.impromptu.admin.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 石鹏
 * @date 2025/2/19 17:28
 */
@Component
public class RedisTask {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * 定时检查Redis连接，每隔30s检查一次
     */
    @Scheduled(fixedRate = 30000)
    public void validateConnection() {
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisConnectionFactory;
        lettuceConnectionFactory.validateConnection();
        System.out.println("Redis连接成功！");
    }
}
