package com.sky.controller.admin;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/10 20:42
 **/

import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类管理
 */
@RestController
@RequestMapping("category")
@Slf4j
@Api(tags = "分类管理相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


}
