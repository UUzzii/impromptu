package com.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.common.entity.Dict;
import com.common.enums.DictEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 字典工具类
 * @author 石鹏
 * @date 2025/2/20 10:48
 */
public class DictUtil {

    /** 所属系统：admin */
    public static final String FROM_SYSTEM_ADMIN = "admin";

    private static final RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("redisTemplate");

    /**
     * 生成key
     * @param fromSystem
     * @param code
     * @return
     */
    public static String key(String fromSystem, String code) {
        return "dict:" + fromSystem + ":" + code;
    }

    /**
     * 根据字典枚举和key获取value
     * @param dictEnum
     * @param k
     * @return
     */
    public static String getValue(DictEnum dictEnum, Object k) {
        if (k == null || StringUtils.isBlank(k.toString())) {
            return null;
        }
        // 从缓存中获取数据
        String value = (String) redisTemplate.opsForValue().get(key(dictEnum.getFromSystem(), dictEnum.getCode()));
        if (StringUtils.isBlank(value)) {
            return null;
        }
        // 解析json
        List<Dict> dictList = JSONObject.parseObject(value, new TypeReference<List<Dict>>() {});
        for (Dict dict : dictList) {
            if (k.toString().equals(dict.getK())) {
                return dict.getV();
            }
        }
        return null;
    }
}
