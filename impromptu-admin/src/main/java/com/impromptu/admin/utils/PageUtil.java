package com.impromptu.admin.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.dto.PageDTO;

/**
 * 分页工具类
 * @author 石鹏
 * @date 2025/1/6 17:39
 */
public class PageUtil {

    public static <T> IPage<T> page(PageDTO pageDTO) {
        return new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
    }
}
