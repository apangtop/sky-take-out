package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/14 15:06
 **/
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应的套餐id
     * @param ids
     * @return
     */
   List<Long> getSetmealIdsByDishIds(List<Long> ids);

    /**
     * 根据套餐id查询对应的菜品
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id=#{id}" )
    List<SetmealDish> getDishBySetMealId(Long id);
}
