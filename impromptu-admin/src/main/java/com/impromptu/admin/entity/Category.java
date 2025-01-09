package com.impromptu.admin.entity;

import com.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类表(Category)实体类
 *
 * @author sp
 * @since 2025-01-09 09:38:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends BaseEntity {

    private static final long serialVersionUID = -15900435863682751L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;
}

