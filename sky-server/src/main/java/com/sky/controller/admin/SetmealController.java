package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/15 16:01
 **/
@RestController("AdminSetmealController")
@Api(tags = "套餐相关接口")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService service;

     @PostMapping
     @ApiOperation("新增套餐")
     @CacheEvict(cacheNames = "categoryCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        service.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     *
     * 分页查询
     */
    @ApiOperation("套餐分页查询")
    @GetMapping("page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        log.info("套餐分页查询 :{}",setmealPageQueryDTO);
        PageResult pageResult=service.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    @ApiOperation("套餐修改")
    @PutMapping()
    public Result update(@RequestBody SetmealDTO setmealDTO)
    {
        service.update(setmealDTO);
        return Result.success();
    }

    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id)
    {
        SetmealVO setmealVO =service.getById(id);
        return Result.success(setmealVO);

    }

    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    @ApiOperation("套餐起售、停售")
    @PostMapping("/status/{status}")
    public Result StartOrStopSetmeal(@PathVariable String status,Long id){
        service.StartOrStopSetmeal(status,id);
        return Result.success();
    }

    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    @ApiOperation("批量删除套餐")
    @DeleteMapping("")
    public Result Delete(@RequestParam List<Long> ids)
    {
        service.DeleteByIds(ids);
        return Result.success();
    }




}
