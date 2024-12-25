package com.impromptu.translate.service;

import com.alibaba.fastjson2.JSONObject;
import com.impromptu.translate.dto.TranslateDTO;
import com.impromptu.translate.utils.TranslateUtil;
import org.springframework.stereotype.Service;

/**
 * 翻译服务
 * @author 石鹏
 * @date 2024/11/14 9:06
 */
@Service
public class TranslateService {

    /**
     * 翻译
     * @param translateDTO
     * @return
     */
    public JSONObject translate(TranslateDTO translateDTO) {
        JSONObject result = new JSONObject();
        result.put("baidu", TranslateUtil.baidu(translateDTO));
        result.put("youdao", TranslateUtil.youdao(translateDTO));
        return result;
    }
}
