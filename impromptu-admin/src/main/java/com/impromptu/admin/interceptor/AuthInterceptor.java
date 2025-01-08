package com.impromptu.admin.interceptor;

import com.common.enums.ResultEnum;
import com.common.exception.BusinessException;
import com.impromptu.admin.entity.AdminUser;
import com.impromptu.admin.utils.AuthUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 * @author 石鹏
 * @date 2025/1/8 14:50
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(ResultEnum.NO_LOGIN);
        }

        // 校验token
        AdminUser adminUser = (AdminUser) redisTemplate.opsForValue().get(AuthUtil.tokenKey(token));
        if (adminUser == null) {
            throw new BusinessException(ResultEnum.NO_LOGIN);
        }

        // 将用户信息存入ThreadLocal
        AuthUtil.set(adminUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthUtil.remove();
    }
}
