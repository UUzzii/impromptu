package com.impromptu.outdoor.controller;

import com.common.result.ResultVO;
import com.impromptu.outdoor.entity.User;
import com.impromptu.outdoor.entity.dto.LoginDTO;
import com.impromptu.outdoor.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户表(User)表控制层
 *
 * @author sp
 * @since 2024-12-30 10:52:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注册或登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/registerOrLogin")
    public ResultVO<?> registerOrLogin(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.registerOrLogin(loginDTO);
    }

    /**
     * 分页查询
     * @param user
     * @return
     */
    @PostMapping("/list")
    public ResultVO<?> list(@RequestBody User user) {
        return userService.list(user);
    }
}

