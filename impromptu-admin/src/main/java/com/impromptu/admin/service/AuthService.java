package com.impromptu.admin.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.common.entity.BaseEntity;
import com.common.result.ResultUtil;
import com.common.result.ResultVO;
import com.impromptu.admin.dto.LoginDTO;
import com.impromptu.admin.entity.AdminUser;
import com.impromptu.admin.utils.AuthUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 石鹏
 * @date 2025/1/7 11:41
 */
@Service
public class AuthService {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<?> login(LoginDTO loginDTO) {
        // 校验参数
        if (StringUtils.isBlank(loginDTO.getAccount()) || StringUtils.isBlank(loginDTO.getPassword())) {
            return ResultUtil.error("账号或密码不能为空");
        }

        // 验证账号密码
        AdminUser adminUser = adminUserService.getOne(Wrappers.lambdaQuery(AdminUser.class)
                .eq(BaseEntity::getStatus, 1)
                .eq(AdminUser::getAccount, loginDTO.getAccount())
                .eq(AdminUser::getPassword, SecureUtil.md5(loginDTO.getPassword())));
        if (adminUser == null) {
            return ResultUtil.error("账号或密码错误");
        }

        // 生成token
        String token = IdUtil.simpleUUID();
        // 缓存token
        redisTemplate.opsForValue().set(AuthUtil.tokenKey(token), adminUser);

        return ResultUtil.success(token);
    }
}
