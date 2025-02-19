package com.impromptu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.result.ResultVO;
import com.impromptu.admin.entity.Menu;

/**
 * 菜单表(Menu)表服务接口
 *
 * @author sp
 * @since 2025-02-05 14:24:52
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树
     * @return
     */
    ResultVO<?> tree();
}

