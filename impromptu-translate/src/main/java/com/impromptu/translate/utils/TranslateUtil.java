package com.impromptu.translate.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.impromptu.translate.dto.TranslateDTO;
import com.impromptu.translate.properties.TranslateProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 翻译工具类
 * @author 石鹏
 * @date 2024/11/13 17:36
 */
@Slf4j
public class TranslateUtil {

    private static final TranslateProperties translateProperties = SpringUtil.getBean(TranslateProperties.class);

    public static void main(String[] args) {
        TranslateDTO translateDTO = new TranslateDTO();
        translateDTO.setContent("common");
        translateDTO.setFrom("");
        translateDTO.setTo("zh");
        System.out.println(baidu(translateDTO));
    }

    /**
     * 百度翻译
     * @param translateDTO
     * @return
     */
    public static List<String> baidu(TranslateDTO translateDTO) {
        List<String> resultList = Lists.newArrayList();
        TranslateProperties.TranslateItem baidu = translateProperties.getBaidu();

        JSONObject paramMap = new JSONObject();
        paramMap.put("q", translateDTO.getContent());
        paramMap.put("from", StringUtils.isNotBlank(translateDTO.getFrom()) ? translateDTO.getFrom() : "auto");
        paramMap.put("to", translateDTO.getTo());
        paramMap.put("appid", baidu.getAppId());
        paramMap.put("salt", IdUtil.simpleUUID());

        // 签名
        String sign = baidu.getAppId() + translateDTO.getContent() + paramMap.getString("salt") + baidu.getSecretKey();
        paramMap.put("sign", SecureUtil.md5(sign));

        try {
            // 请求API
            String post = HttpUtil.post(baidu.getUrl(), paramMap);
            log.info("百度翻译请求：{}\t百度翻译结果：{}", translateDTO, post);

            // 解析响应
            JSONObject result = JSONObject.parseObject(post);
            if (result.containsKey("error_code")) {
                resultList.add("百度翻译错误：" + post);
                return resultList;
            }

            JSONArray transResult = result.getJSONArray("trans_result");
            for (int i = 0; i < transResult.size(); i++) {
                resultList.add(UnicodeUtil.toString(transResult.getJSONObject(i).getString("dst")));
            }
            return resultList;
        } catch (Exception e) {
            log.error("百度翻译异常", e);
            resultList.add("百度翻译异常");
            return resultList;
        }
    }

    /**
     * 有道翻译
     * @param translateDTO
     * @return
     */
    public static List<String> youdao(TranslateDTO translateDTO) {
        List<String> resultList = Lists.newArrayList();
        TranslateProperties.TranslateItem youdao = translateProperties.getYoudao();

        // 翻译内容
        String content = translateDTO.getContent();

        JSONObject paramMap = new JSONObject();
        paramMap.put("q", content);
        paramMap.put("from", StringUtils.isNotBlank(translateDTO.getFrom()) ? translateDTO.getFrom() : "auto");
        paramMap.put("to", translateDTO.getTo());

        // 应用id
        paramMap.put("appKey", youdao.getAppId());
        // 盐
        paramMap.put("salt", IdUtil.simpleUUID());
        // 当前时间戳
        paramMap.put("curtime", DateUtil.currentSeconds());
        // 签名类型
        paramMap.put("signType", "v3");

        // 签名
        String input;
        if (content.length() <= 20) {
            input = content;
        } else {
            input = content.substring(0, 10) + content.length() + content.substring(content.length() - 10);
        }

        String sign = youdao.getAppId() + input + paramMap.getString("salt") + paramMap.getString("curtime") + youdao.getSecretKey();
        paramMap.put("sign", SecureUtil.sha256(sign));

        try {
            // 请求API
            String post = HttpUtil.post(youdao.getUrl(), paramMap);
            log.info("有道翻译请求：{}\t有道翻译结果：{}", translateDTO, post);

            // 解析响应
            JSONObject result = JSONObject.parseObject(post);
            if (!"0".equals(result.getString("errorCode"))) {
                resultList.add("有道翻译错误：" + post);
                return resultList;
            }

            JSONArray transResult = result.getJSONArray("translation");
            for (int i = 0; i < transResult.size(); i++) {
                resultList.add(transResult.getString(i));
            }
            return resultList;
        } catch (Exception e) {
            log.error("有道翻译异常", e);
            resultList.add("有道翻译异常");
            return resultList;
        }
    }
}
