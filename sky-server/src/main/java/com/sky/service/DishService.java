package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/13 21:25
 **/
public interface DishService {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
