package com.impromptu.translate.enums;

import lombok.Getter;

/**
 * 语种枚举
 * @author 石鹏
 * @date 2024/11/14 9:27
 */
@Getter
public enum LanguageEnumYouDao {

    ar("阿拉伯语", "ar"),
    de("德语", "de"),
    en("英语,", "en"),
    es("西班牙语", "es"),
    fr("法语", "fr"),
    hi("印地语", "hi"),
    id("印度尼西亚语", "id"),
    it("意大利语", "it"),
    ja("日语", "ja"),
    ko("韩语", "ko"),
    nl("荷兰语", "nl"),
    pt("葡萄牙语", "pt"),
    ru("俄语", "ru"),
    th("泰语", "th"),
    vi("越南语", "vi"),
    zh_CHS("简体中文", "zh-CHS"),
    zh_CHT("繁体中文", "zh-CHT"),
    af("南非荷兰语", "af"),
    cs("捷克语", "cs"),
    da("丹麦语", "da"),
    el("希腊语", "el"),
    fa("波斯语", "fa"),
    fi("芬兰语", "fi"),
    ga("爱尔兰语", "ga"),
    haw("夏威夷语", "haw"),
    he("希伯来语", "he"),
    hr("克罗地亚语", "hr"),
    hu("匈牙利语", "hu"),
    hy("亚美尼亚语", "hy"),
    is("冰岛语", "is"),
    jw("爪哇语", "jw"),
    la("拉丁语", "la"),
    mn("蒙古语", "mn"),
    my("缅甸语", "my"),
    ne("尼泊尔语", "ne"),
    no("挪威语", "no"),
    pl("波兰语", "pl"),
    sr_Cyrl("塞尔维亚语(西里尔文)", "sr-Cyrl"),
    sr_Latn("塞尔维亚语(拉丁文)", "sr-Latn"),
    sv("瑞典语", "sv"),
    ta("泰米尔语", "ta"),
    tl("菲律宾语", "tl"),
    tr("土耳其语", "tr"),
    uz("乌兹别克语", "uz"),
    yue("粤语", "yue");

    /** 名称 */
    private final String name;

    /** 编号 */
    private final String code;

    LanguageEnumYouDao(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
