package com.impromptu.translate.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 翻译配置
 * @author 石鹏
 * @date 2024/12/24 16:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "translate")
public class TranslateProperties {

    /** 百度 */
    private TranslateItem baidu;

    /** 有道 */
    private TranslateItem youdao;

    @Data
    public static class TranslateItem {
        private String url;
        private String appId;
        private String secretKey;
    }
}
