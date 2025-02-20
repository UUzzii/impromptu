package com.impromptu.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.common.entity.Dict;
import com.common.enums.DictEnum;
import com.common.result.ResultVO;
import com.common.service.DictService;
import com.common.utils.DictUtil;
import com.google.common.collect.Lists;
import com.impromptu.admin.dao.DictDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典表(Dict)表服务实现类
 *
 * @author sp
 * @since 2025-02-20 10:22:13
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostConstruct
    public void initDictCache() {
        log.info("开始加载字典数据到缓存...");

        int size = 0;
        try {
            // 查询所有字典数据
            LambdaQueryWrapper<Dict> eq = Wrappers.lambdaQuery(Dict.class);
            for (DictEnum value : DictEnum.values()) {
                eq.or(wrapper -> wrapper.eq(Dict::getDictCode, value.getCode()).eq(Dict::getDictFromSystem, value.getFromSystem()));
            }
            List<Dict> dictList = dictDao.selectList(eq);
            if (CollUtil.isEmpty(dictList)) {
                return;
            }

            // 存储到redis中
            dictList.stream()
                    .collect(Collectors.groupingBy(item -> DictUtil.key(item.getDictFromSystem(), item.getDictCode())))
                    .forEach((k, v) -> redisTemplate.opsForValue().set(k, JSONObject.toJSONString(v)));

            size = dictList.size();
        } catch (Exception e) {
            log.error("加载字典数据到缓存失败", e);
        } finally {
            log.info("字典缓存加载完成，共 {} 条数据。", size);
        }
    }

    @Override
    public ResultVO<?> all() {
        // 字典集合
        List<Dict> dictList = Lists.newArrayList();

        // 从redis中查询所有前缀为admin:的数据
        ScanOptions options = ScanOptions.scanOptions()
                .match(DictUtil.key(DictUtil.FROM_SYSTEM_ADMIN, "*"))
                .count(100)  // 每次扫描返回的元素数量
                .build();
        redisTemplate.execute((RedisConnection connection) -> {
            // 获取游标
            Cursor<byte[]> cursor = connection.scan(options);
            while (cursor.hasNext()) {
                // 根据key查询value
                String value = (String) redisTemplate.opsForValue().get(new String(cursor.next(), StandardCharsets.UTF_8));
                if (StringUtils.isNotBlank(value)) {
                    dictList.addAll(JSONObject.parseObject(value, new TypeReference<List<Dict>>() {}));
                }
            }
            return null;
        });

        if (CollUtil.isEmpty(dictList)) {
            // redis中没有，查询数据库
            dictList.addAll(dictDao.selectList(Wrappers.lambdaQuery(Dict.class).eq(Dict::getDictFromSystem, DictUtil.FROM_SYSTEM_ADMIN)));
            // 添加到redis中
            dictList.stream()
                    .collect(Collectors.groupingBy(item -> DictUtil.key(item.getDictFromSystem(), item.getDictCode())))
                    .forEach((k, v) -> redisTemplate.opsForValue().set(k, JSONObject.toJSONString(v)));
        }
        return ResultVO.success(dictList.stream().collect(Collectors.groupingBy(item -> DictUtil.key(item.getDictFromSystem(), item.getDictCode()))));
    }
}

