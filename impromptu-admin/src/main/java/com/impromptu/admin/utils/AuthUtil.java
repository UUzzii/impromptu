package com.impromptu.admin.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.common.enums.ResultEnum;
import com.common.exception.BusinessException;
import com.impromptu.admin.entity.AdminUser;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 权限工具类
 * @author 石鹏
 * @date 2025/1/8 14:57
 */
public class AuthUtil {

    private static final ThreadLocal<AdminUser> threadLocal = new ThreadLocal<>();

    private static final RedisTemplate<String, Object> REDIS_TEMPLATE = SpringUtil.getBean("redisTemplate");

    /** 过期时间 */
    private static final int timeout = 15;

    public static String tokenKey(String token) {
        return "token:" + token;
    }

    /**
     * 登录,设置token
     * @param adminUser
     */
    public static String login(AdminUser adminUser) {
        // 生成token
        String token = IdUtil.simpleUUID();
        // 缓存token
        REDIS_TEMPLATE.opsForValue().set(tokenKey(token), adminUser, timeout, TimeUnit.HOURS);
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static void check(String token) {
        token = tokenKey(token);
        // 校验token
        AdminUser adminUser = (AdminUser) REDIS_TEMPLATE.opsForValue().get(token);
        if (adminUser == null) {
            throw new BusinessException(ResultEnum.NO_LOGIN);
        }

        // 刷新token活跃
        REDIS_TEMPLATE.expire(token, timeout, TimeUnit.HOURS);

        // 设置到当前线程中
        set(adminUser);
    }

    public static void set(AdminUser adminUser) {
        threadLocal.set(adminUser);
    }

    public static AdminUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
