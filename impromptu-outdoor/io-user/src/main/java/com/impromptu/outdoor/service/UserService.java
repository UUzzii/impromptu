package com.impromptu.outdoor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.result.ResultVO;
import com.impromptu.outdoor.entity.User;
import com.impromptu.outdoor.entity.dto.LoginDTO;

/**
 * 用户表(User)表服务接口
 *
 * @author sp
 * @since 2024-12-30 10:50:57
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册或登录
     * @param loginDTO
     * @return
     */
    ResultVO<?> registerOrLogin(LoginDTO loginDTO);
}

