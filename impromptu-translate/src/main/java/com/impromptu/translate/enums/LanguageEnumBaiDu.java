package com.impromptu.translate.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 语种枚举
 * @author 石鹏
 * @date 2024/11/14 9:27
 */
@Getter
public enum LanguageEnumBaiDu {

    yueyu(LanguageEnum.yueyu.getCode(), "粤语", "yueyu"),
    kor(LanguageEnum.ko.getCode(), "韩语", "kor"),
    th(LanguageEnum.th.getCode(), "泰语", "th"),
    pt("葡萄牙语", "pt"),
    el("希腊语", "el"),
    bul("保加利亚语", "bul"),
    fin("芬兰语", "fin"),
    slo("斯洛文尼亚语", "slo"),
    cht(LanguageEnum.cht.getCode(), "繁体中文", "cht"),
    zh(LanguageEnum.zh.getCode(), "中文", "zh"),
    wyw("文言文", "wyw"),
    fra(LanguageEnum.fra.getCode(), "法语", "fra"),
    ara(LanguageEnum.ara.getCode(), "阿拉伯语", "ara"),
    de(LanguageEnum.de.getCode(), "德语", "de"),
    nl(LanguageEnum.nl.getCode(), "荷兰语", "nl"),
    est("爱沙尼亚语", "est"),
    cs("捷克语", "cs"),
    swe("瑞典语", "swe"),
    vie("越南语", "vie"),
    en(LanguageEnum.en.getCode(), "英语", "en"),
    jp(LanguageEnum.jp.getCode(), "日语", "jp"),
    spa(LanguageEnum.spa.getCode(), "西班牙语", "spa"),
    ru(LanguageEnum.ru.getCode(), "俄语", "ru"),
    it("意大利语", "it"),
    pl("波兰语", "pl"),
    dan("丹麦语", "dan"),
    rom("罗马尼亚语", "rom"),
    hu("匈牙利语", "hu");

    /** 来源，用于映射 */
    private String from;

    /** 名称 */
    private final String name;

    /** 编号 */
    private final String code;

    LanguageEnumBaiDu(String name, String code) {
        this.name = name;
        this.code = code;
    }

    LanguageEnumBaiDu(String from, String name, String code) {
        this.from = from;
        this.name = name;
        this.code = code;
    }

    public static String getCodeByFrom(String from) {
        if (StringUtils.isBlank(from)) {
            throw new RuntimeException("from为空");
        }
        for (LanguageEnumBaiDu value : LanguageEnumBaiDu.values()) {
            if (StringUtils.isNotBlank(value.getFrom()) && value.getFrom().equals(from)) {
                return value.getCode();
            }
        }
        return from;
    }
}
