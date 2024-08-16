package com.sky.controller.admin;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/10 20:42
 **/

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("admin/category")
@Slf4j
@Api(tags = "分类管理相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 修改分类
     * @return
     */
    @PutMapping()
    @ApiOperation("修改分类信息")
    public Result update(@RequestBody  CategoryDTO categoryDTO)
    {
        log.info("修改分类");
        categoryService.update(categoryDTO);
        return Result.success();
    }


    /**
     * 分类分页查询
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    public Result<PageResult> Page(CategoryPageQueryDTO categoryPageQueryDTO)
    {
        log.info("分类分页查询,参数为:",categoryPageQueryDTO);
        PageResult pageResult=categoryService.PageQuery(categoryPageQueryDTO);
        log.info("查询的数据为:{}",pageResult);
        return Result.success(pageResult);
    }

    /**
     * 启用，禁用分类
     * @return
     */
    @ApiOperation("禁用或启用分类")
    @PostMapping( "status/{status}")
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)

    public Result StratOrStop(@PathVariable Integer status,Long id)
    {
        log.info("启动或禁用分类");
        categoryService.StartOrStop(status,id);
        return Result.success();
    }


    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping()
    @ApiOperation("新增分类")
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)

    public Result save(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("新增分类：{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 按id删除分类
     * @return
     */
    @ApiOperation("按id删除分类")
    @DeleteMapping()
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    public Result Delete(Long id)
    {
        log.info("按id删除分类 id: {}"+id);
        categoryService.Delete(id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)

    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
