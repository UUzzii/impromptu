package com.impromptu.translate.enums;

import lombok.Getter;

/**
 * 语种枚举
 * @author 石鹏
 * @date 2024/11/14 9:27
 */
@Getter
public enum LanguageEnumBaiDu {

    yueyu("粤语", "yueyu"),
    kor("韩语", "kor"),
    th("泰语", "th"),
    pt("葡萄牙语", "pt"),
    el("希腊语", "el"),
    bul("保加利亚语", "bul"),
    fin("芬兰语", "fin"),
    slo("斯洛文尼亚语", "slo"),
    cht("繁体中文", "cht"),
    zh("中文", "zh"),
    wyw("文言文", "wyw"),
    fra("法语", "fra"),
    ara("阿拉伯语", "ara"),
    de("德语", "de"),
    nl("荷兰语", "nl"),
    est("爱沙尼亚语", "est"),
    cs("捷克语", "cs"),
    swe("瑞典语", "swe"),
    vie("越南语", "vie"),
    en("英语", "en"),
    jp("日语", "jp"),
    spa("西班牙语", "spa"),
    ru("俄语", "ru"),
    it("意大利语", "it"),
    pl("波兰语", "pl"),
    dan("丹麦语", "dan"),
    rom("罗马尼亚语", "rom"),
    hu("匈牙利语", "hu");

    /** 名称 */
    private final String name;

    /** 编号 */
    private final String code;

    LanguageEnumBaiDu(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
