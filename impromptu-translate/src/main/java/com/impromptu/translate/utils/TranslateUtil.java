package com.impromptu.translate.utils;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.impromptu.translate.dto.TranslateDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 翻译工具类
 * @author 石鹏
 * @date 2024/11/13 17:36
 */
@Slf4j
public class TranslateUtil {

    private static final String BAIDU_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";
    private static final String BAIDU_APPID = "";
    private static final String BAIDU_SECRET_KEY = "";

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
    public static String baidu(TranslateDTO translateDTO) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("q", translateDTO.getContent());
        paramMap.put("from", StringUtils.isNotBlank(translateDTO.getFrom()) ? translateDTO.getFrom() : "auto");
        paramMap.put("to", translateDTO.getTo());
        paramMap.put("appid", BAIDU_APPID);
        paramMap.put("salt", IdUtil.simpleUUID());

        // 签名
        String sign = BAIDU_APPID + translateDTO.getContent() + paramMap.getString("salt") + BAIDU_SECRET_KEY;
        paramMap.put("sign", SecureUtil.md5(sign));

        try {
            // 请求API
            String post = HttpUtil.post(BAIDU_URL, paramMap);
            log.info("百度翻译请求：{}\t百度翻译结果：{}", translateDTO, post);

            // 解析响应
            JSONObject result = JSONObject.parseObject(post);
            if (result.containsKey("error_code")) {
                // 错误
                return post;
            }

            // 成功，将多个结果拼接后返回
            StringBuilder sb = new StringBuilder();

            JSONArray transResult = result.getJSONArray("trans_result");
            for (int i = 0; i < transResult.size(); i++) {
                sb.append(UnicodeUtil.toString(transResult.getJSONObject(i).getString("dst"))).append("、");
            }

            return sb.deleteCharAt(sb.length() - 1).toString();
        } catch (Exception e) {
            log.error("百度翻译异常", e);
            return "翻译异常";
        }
    }

}
