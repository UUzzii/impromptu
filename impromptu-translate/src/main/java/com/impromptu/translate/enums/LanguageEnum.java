package com.impromptu.translate.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用语言枚举，用于映射
 * @author 石鹏
 * @date 2024/12/26 16:31
 */
@Getter
@ToString
public enum LanguageEnum {

    zh("中文", "zh"),
    cht("繁体中文", "cht"),
    yueyu("粤语", "yueyu"),
    ko("韩语", "ko"),
    th("泰语", "th"),
    fra("法语", "fra"),
    ara("阿拉伯语", "ara"),
    de("德语", "de"),
    nl("荷兰语", "nl"),
    en("英语", "en"),
    jp("日语", "jp"),
    spa("西班牙语", "spa"),
    ru("俄语", "ru");

    /** 名称 */
    private final String name;

    /** 编号 */
    private final String code;

    LanguageEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static List<Map<String, String>> toList() {
        return Arrays.stream(values())
                .map(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("code", item.getCode());
                    map.put("name", item.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
