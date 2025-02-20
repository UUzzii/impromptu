package com.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表(Dict)实体类
 *
 * @author sp
 * @since 2025-02-20 10:24:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 319788006838192205L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 字典code
     */
    private String dictCode;

    /**
     * 所属系统
     */
    private String dictFromSystem;

    /**
     * key
     */
    private String k;

    /**
     * value
     */
    private String v;
}

