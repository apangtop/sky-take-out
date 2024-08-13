package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/10 20:45
 **/
public interface CategoryService {
    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
     PageResult PageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 分类修改
     * @param categoryService
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用或禁用分类
     * @param status
     * @param id
     */
    void StartOrStop(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    void Delete(Long id);

    List<Category> list(Integer type);
}
