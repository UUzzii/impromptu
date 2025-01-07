package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.dto.LoginDTO;
import com.impromptu.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 石鹏
 * @date 2025/1/7 11:40
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    /**
     * 登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResultVO<?> login(@RequestBody LoginDTO dto) {
        return authService.login(dto);
    }
}
