package com.common.vo;

import lombok.Data;

/**
 * 文件响应DTO
 * @author 石鹏
 * @date 2025/2/25 11:42
 */
@Data
public class FileResponseVO {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件访问地址
     */
    private String fileUrl;
}
