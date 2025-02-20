package com.common.service;

import com.common.result.ResultVO;

/**
 * 字典表(Dict)表服务接口
 *
 * @author sp
 * @since 2025-02-20 10:22:13
 */
public interface DictService {

    /**
     * 获取所有字典
     * @return
     */
    ResultVO<?> all();
}

