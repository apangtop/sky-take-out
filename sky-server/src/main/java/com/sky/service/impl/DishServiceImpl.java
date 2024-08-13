package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Utilities;
import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/13 21:26
 **/
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //向菜品表插入一条数据
        dishMapper.Insert(dish);
        //获取insert语句生成的主键值
        Long id = dish.getId();


        //向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null ||flavors.size()>0)
        {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }



    }
}
