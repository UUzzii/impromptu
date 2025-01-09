package com.impromptu.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.enums.ResultEnum;
import com.common.exception.BusinessException;
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
        this.check(category);

        category.setCreateTime(new Date());
        category.setUpdateTime(category.getCreateTime());
        baseMapper.insert(category);

        return ResultVO.success();
    }

    private void check(Category category) {
        if (category.getParentId() == null) {
            category.setParentId(0);
        } else if (category.getParentId() != 0) {
            Category parent = baseMapper.selectById(category.getParentId());
            if (parent == null) {
                throw new BusinessException(ResultEnum.ERROR, "父级分类不存在");
            }
        }

        // 查询名称是否重复
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(Category.class)
                .eq(Category::getParentId, category.getParentId())
                .eq(Category::getName, category.getName())
                .ne(category.getId() != null, Category::getId, category.getId()));
        if (exists) {
            throw new BusinessException(ResultEnum.ERROR, "分类名称已存在");
        }
    }

    @Override
    public ResultVO<?> update(Category category) {
        Category old = baseMapper.selectById(category.getId());
        if (old == null) {
            return ResultVO.error("分类不存在");
        }

        // 校验
        this.check(category);

        category.setUpdateTime(new Date());
        baseMapper.updateById(category);

        return ResultVO.success();
    }

    @Override
    public ResultVO<?> delete(Category category) {
        Category old = baseMapper.selectById(category.getId());
        if (old == null) {
            return ResultVO.error("分类不存在");
        }

        // 查询是否有子分类
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(Category.class)
                .eq(Category::getParentId, category.getId()));
        if (exists) {
            return ResultVO.error("该分类下有子分类，无法删除");
        }

        baseMapper.deleteById(category.getId());

        return ResultVO.success();
    }
}

