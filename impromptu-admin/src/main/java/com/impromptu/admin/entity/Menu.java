package com.impromptu.admin.entity;

import com.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单表(Menu)实体类
 *
 * @author sp
 * @since 2025-02-05 14:24:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseEntity {

    private static final long serialVersionUID = -49270220244703605L;


    private Integer id;

    /**
     * 上级id
     */
    private Integer parentId;

    /**
     * code
     */
    private String code;

    /**
     * icon
     */
    private String icon;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型：0菜单，1按钮
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 路由
     */
    private String url;
}

