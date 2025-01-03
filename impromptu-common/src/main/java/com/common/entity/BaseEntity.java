package com.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * @author 石鹏
 * @date 2024/12/27 17:20
 */
@Data
public class BaseEntity implements Serializable {

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}
