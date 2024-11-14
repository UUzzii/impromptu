package com.impromptu.translate.dto;

import lombok.Data;

/**
 * 翻译 DTO
 * @author 石鹏
 * @date 2024/11/13 17:42
 */
@Data
public class TranslateDTO {

    /** 翻译内容 */
    private String content;

    /** 翻译源语言 */
    private String from;

    /** 翻译目标语言 */
    private String to;
}
