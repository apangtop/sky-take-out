package com.sky.controller.admin;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/13 20:13
 **/
@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class dishController {

    @Autowired
   private DishService service;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping()
    public Result save(@RequestBody DishDTO dishDTO)
    {
        log.info("新增菜品");
        service.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> Page(DishPageQueryDTO dishPageQueryDTO)
    {
        log.info("菜品分页查询: {}",dishPageQueryDTO);
        PageResult result=service.pageQuery(dishPageQueryDTO);
        return Result.success(result);
    }

    @ApiOperation("批量删除多个菜品")
    @DeleteMapping()
    public Result delete(@RequestParam List<Long> ids)
    {
        log.info("删除菜品: {}",ids);
        service.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品: {}",id);
        DishVO dishVO = service.getIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @ApiOperation("修改菜品")
    @PutMapping()
    public Result update(@RequestBody DishDTO dishDTO)
    {
        log.info("修改菜品: {}",dishDTO);
        service.updateWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("菜品起售停售")
    @PostMapping("status/{status}")
    public Result StopOrStartDish(@PathVariable String status,String id)
    {
        log.info("菜品禁用或启用");
        service.StopOrStartDish(status,id);
        return Result.success();
    }



}
