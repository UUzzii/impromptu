package com.impromptu.admin.utils;

import com.impromptu.admin.entity.AdminUser;

/**
 * 权限工具类
 * @author 石鹏
 * @date 2025/1/8 14:57
 */
public class AuthUtil {

    private static final ThreadLocal<AdminUser> threadLocal = new ThreadLocal<>();

    public static String tokenKey(String token) {
        return "token:" + token;
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
