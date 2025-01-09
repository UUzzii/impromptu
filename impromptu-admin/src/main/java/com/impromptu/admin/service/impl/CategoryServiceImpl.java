package com.impromptu.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.result.ResultVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.impromptu.admin.dao.CategoryDao;
import com.impromptu.admin.dto.CategorySelectDTO;
import com.impromptu.admin.entity.Category;
import com.impromptu.admin.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分类表(Category)表服务实现类
 *
 * @author sp
 * @since 2025-01-09 09:38:46
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {


    @Override
    public ResultVO<?> tree(CategorySelectDTO dto) {
        // 查询所有分类
        List<Category> categoryList = baseMapper.selectList(Wrappers.lambdaQuery(Category.class)
                .like(StringUtils.isNotBlank(dto.getName()), Category::getName, dto.getName())
                .like(StringUtils.isNotBlank(dto.getRemark()), Category::getRemark, dto.getRemark())
                .orderByAsc(Category::getSort));
        if (CollUtil.isEmpty(categoryList)) {
            return ResultVO.success();
        }

        // 转换成树
        List<TreeNode<Integer>> treeNodeList = Lists.newArrayList();
        categoryList.forEach(category -> {
            Map<String, Object> extra = Maps.newHashMap();
            extra.put("image", category.getImage());
            extra.put("remark", category.getRemark());
            extra.put("createTime", category.getCreateTime());
            extra.put("updateTime", category.getUpdateTime());
            TreeNode<Integer> treeNode = new TreeNode<>(category.getId(), category.getParentId(), category.getName(), category.getSort()).setExtra(extra);
            treeNodeList.add(treeNode);
        });
        return ResultVO.success(TreeUtil.build(treeNodeList, 0));
    }

    @Override
    public ResultVO<?> add(Category category) {
        if (category.getParentId() == null) {
            category.setParentId(0);
        } else {
            Category parent = baseMapper.selectById(category.getParentId());
            if (parent == null) {
                return ResultVO.error("父级分类不存在");
            }
        }

        // 查询名称是否重复
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(Category.class)
                .eq(Category::getParentId, category.getParentId())
                .eq(Category::getName, category.getName()));
        if (exists) {
            return ResultVO.error("分类名称已存在");
        }

        category.setCreateTime(new Date());
        category.setUpdateTime(category.getCreateTime());
        baseMapper.insert(category);

        return ResultVO.success();
    }
}

