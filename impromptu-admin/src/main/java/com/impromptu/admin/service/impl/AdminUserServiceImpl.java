package com.impromptu.admin.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.entity.BaseEntity;
import com.common.enums.ResultEnum;
import com.common.exception.BusinessException;
import com.common.result.ResultUtil;
import com.common.result.ResultVO;
import com.impromptu.admin.dao.AdminUserDao;
import com.impromptu.admin.dto.AdminUserDTO;
import com.impromptu.admin.dto.AdminUserSelectDTO;
import com.impromptu.admin.entity.AdminUser;
import com.impromptu.admin.service.AdminUserService;
import com.impromptu.admin.utils.PageUtil;
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
        AdminUser adminUser = this.pre(dto);
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

    /**
     * 前置工作
     * @param dto
     * @return
     */
    private AdminUser pre(AdminUserDTO dto) {
        if (!Validator.isMobile(dto.getPhone())) {
            throw new BusinessException(ResultEnum.ERROR, "手机号格式不正确");
        }
        // 密码md5加密
        dto.setPassword(SecureUtil.md5(dto.getPassword()));

        // 转换dto为entity
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(dto, adminUser);

        return adminUser;
    }

    @Override
    public ResultVO<?> page(AdminUserSelectDTO dto) {
        LambdaQueryWrapper<AdminUser> wrapper = Wrappers.<AdminUser>lambdaQuery()
                .eq(StringUtils.isNotBlank(dto.getAccount()), AdminUser::getAccount, dto.getAccount())
                .like(StringUtils.isNotBlank(dto.getName()), AdminUser::getName, dto.getName())
                .eq(StringUtils.isNotBlank(dto.getPhone()), AdminUser::getPhone, dto.getPhone())
                .eq(dto.getSex() != null, AdminUser::getSex, dto.getSex())
                .eq(dto.getStatus() != null, AdminUser::getStatus, dto.getStatus())
                .ge(dto.getCreateTimeStart() != null, AdminUser::getCreateTime, dto.getCreateTimeStart())
                .le(dto.getCreateTimeEnd() != null, AdminUser::getCreateTime, dto.getCreateTimeEnd())
                .orderByDesc(BaseEntity::getCreateTime);
        return ResultUtil.success(baseMapper.selectPage(PageUtil.page(dto), wrapper));
    }

    @Override
    public ResultVO<?> update(AdminUserDTO dto) {
        AdminUser adminUser = this.pre(dto);
        adminUser.setUpdateTime(new Date());

        baseMapper.updateById(adminUser);

        return ResultUtil.success();
    }
}

