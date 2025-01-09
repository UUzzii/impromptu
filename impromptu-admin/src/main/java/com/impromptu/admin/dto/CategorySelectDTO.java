package com.impromptu.admin.dto;

import com.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类表(Category)表查询DTO
 * @author 石鹏
 * @date 2025/1/9 9:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategorySelectDTO extends PageDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
