package com.impromptu.admin.service.impl;

import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.result.ResultVO;
import com.google.common.collect.Maps;
import com.impromptu.admin.dao.MenuDao;
import com.impromptu.admin.entity.Menu;
import com.impromptu.admin.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表(Menu)表服务实现类
 *
 * @author sp
 * @since 2025-02-05 14:24:52
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {


    @Override
    public ResultVO<?> tree() {
        // 查询所有菜单
        List<Menu> menuList = baseMapper.selectList(Wrappers.lambdaQuery(Menu.class).eq(Menu::getType, 0));
        // 转换成树
        List<TreeNode<Integer>> treeNodeList = menuList.stream().map(menu -> {
            Map<String, Object> extra = Maps.newHashMap();
            extra.put("code", menu.getCode());
            extra.put("icon", menu.getIcon());
            extra.put("url", menu.getUrl());
            return new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getSort()).setExtra(extra);
        }).collect(Collectors.toList());
        return ResultVO.success(TreeUtil.build(treeNodeList, 0));
    }

    @Override
    public ResultVO<?> add(Menu menu) {
        // 如果没有父级,那么默认就是顶级
        if (menu.getParentId() == null) {
            menu.setParentId(0);
        } else if (menu.getParentId() != 0) {
            // 如果有父级,那么需要判断父级是否存在
            Menu parent = baseMapper.selectById(menu.getParentId());
            if (parent == null) {
                return ResultVO.error("父级菜单不存在");
            }
        }

        // 验证code唯一性
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(Menu.class)
                .eq(Menu::getCode, menu.getCode())
                .ne(menu.getId() != null, Menu::getId, menu.getId()));
        if (exists) {
            return ResultVO.error("code已存在");
        }

        baseMapper.insert(menu);

        return ResultVO.success();
    }
}

