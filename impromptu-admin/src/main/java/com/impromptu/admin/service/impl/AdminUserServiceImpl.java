package com.impromptu.admin.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.result.ResultUtil;
import com.common.result.ResultVO;
import com.impromptu.admin.dao.AdminUserDao;
import com.impromptu.admin.dto.AdminUserDTO;
import com.impromptu.admin.entity.AdminUser;
import com.impromptu.admin.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 后台用户表(AdminUser)表服务实现类
 *
 * @author sp
 * @since 2025-01-03 16:47:30
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {

    @Override
    public ResultVO<?> add(AdminUserDTO dto) {
        if (!Validator.isMobile(dto.getPhone())) {
            return ResultUtil.error("手机号格式不正确");
        }
        // 密码md5加密
        dto.setPassword(SecureUtil.md5(dto.getPassword()));

        // 转换dto为entity
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(dto, adminUser);

        adminUser.setStatus(1);
        adminUser.setCreateTime(new Date());
        adminUser.setUpdateTime(adminUser.getCreateTime());

        // 设置默认头像
        if (StringUtils.isBlank(adminUser.getAvatar())) {
            adminUser.setAvatar("https://img.yzcdn.cn/vant/cat.jpeg");
        }

        baseMapper.insert(adminUser);
        return ResultUtil.success();
    }
}

