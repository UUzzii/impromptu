package com.impromptu.admin.controller;

import com.common.service.DictService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 字典表(Dict)表控制层
 *
 * @author sp
 * @since 2025-02-20 10:22:12
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private DictService dictService;
}

