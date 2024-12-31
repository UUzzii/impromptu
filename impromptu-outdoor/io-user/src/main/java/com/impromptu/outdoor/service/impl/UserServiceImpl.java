package com.impromptu.outdoor.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.result.ResultUtil;
import com.common.result.ResultVO;
import com.google.common.collect.Lists;
import com.impromptu.outdoor.dao.UserDao;
import com.impromptu.outdoor.entity.BaseEntity;
import com.impromptu.outdoor.entity.User;
import com.impromptu.outdoor.entity.dto.LoginDTO;
import com.impromptu.outdoor.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author sp
 * @since 2024-12-30 10:50:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final AES aes = new AES("0123456789abcdef".getBytes());


    @Override
    public ResultVO<?> registerOrLogin(LoginDTO loginDTO) {
        if (!Validator.isMobile(loginDTO.getPhone())) {
            return ResultUtil.error("手机号格式错误");
        }

        boolean flag = false;
        // 校验验证码
        if ("111".equals(loginDTO.getCode())) {
            flag = true;
        }
        if (!flag) {
            return ResultUtil.error("验证码错误");
        }

        // 查询是否注册了
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, loginDTO.getPhone()).eq(BaseEntity::getStatus, 1));
        if (user == null) {
            // 自动注册
            user = new User();
            user.setAccount(System.currentTimeMillis() + "");
            user.setPhone(aes.encryptHex(loginDTO.getPhone()));
            user.setPhoneEncrypt(this.phoneEncrypt(loginDTO.getPhone()));
            user.setName("即兴" + RandomUtil.randomString(6));
            user.setAvatar("https://img1.baidu.com/it/u=883487874,2916664100&fm=253&fmt=auto&app=138&f=JPEG?w=263&h=395");
            user.setStatus(1);
            user.setCreateTime(new Date());
            user.setUpdateTime(user.getCreateTime());
            baseMapper.insert(user);
        }

        // 生成token
        String token = IdUtil.simpleUUID();
//        redisTemplate.opsForValue().set(token, user, 7 * 24, TimeUnit.HOURS);

        return ResultUtil.success(token);
    }

    private String phoneEncrypt(String phone) {

        StringBuilder sb = new StringBuilder();

        List<String> list = this.substringLength(phone, 4);
        for (String s : list) {
            sb.append(aes.encryptHex(s));
        }

        return sb.toString();
    }

    private List<String> substringLength(String data, int length) {
        List<String> list = Lists.newArrayList();

        char[] charArray = data.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (i + length > charArray.length) {
                break;
            }
            list.add(data.substring(i, i + length));
        }

        return list;
    }

    @Override
    public ResultVO<?> list(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        if (StringUtils.isNotBlank(user.getPhone())) {
            String encryptHex = aes.encryptHex(user.getPhone());
            lambdaQueryWrapper.and(i -> i.like(User::getPhone, encryptHex).or().like(User::getPhoneEncrypt, encryptHex));
        }
        List<User> userList = baseMapper.selectList(lambdaQueryWrapper);
        return ResultUtil.success(userList);
    }
}

