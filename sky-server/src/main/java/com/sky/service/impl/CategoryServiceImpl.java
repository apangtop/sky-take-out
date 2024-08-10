package com.sky.service.impl;

import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/10 20:46
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

}
