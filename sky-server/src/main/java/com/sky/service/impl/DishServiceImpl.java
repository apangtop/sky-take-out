package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.Utilities;
import java.util.ArrayList;
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

    @Autowired
    private SetmealDishMapper setmealDishMapper;

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
        if(flavors!=null &&flavors.size()>0)
        {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }



    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 菜品的批量删除功能
     * @param ids
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品能否删除------是否存在起售中的菜品
        for (Long id:ids) {
            Dish dish=dishMapper.getById(id);
            if(dish.getStatus()== StatusConstant.ENABLE)
            {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品能否删除-----当前菜品是否被某个套餐关联了
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds!=null &&setmealIds.size()>0)
        {
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除菜品表中的菜品数据
//        for(Long id:ids)
//        {
//            dishMapper.deleteById(id);
//            //删除菜品关联的口味数据
//            dishFlavorMapper.deleteByDishId(id);
//        }

        //根据菜品id集合批量删除菜品数据
        dishMapper.deleteByIds(ids);
        //根据菜品id集合批量删除菜品数据
        dishFlavorMapper.deleteByIds(ids);

    }

    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    @Transactional
    public DishVO getIdWithFlavor(Long id)
    {
        //根据id查询菜品数据
        Dish dish = dishMapper.getById(id);
        //根据菜品id查询口味数据
        List<DishFlavor> list=dishFlavorMapper.getByDishId(id);
        //将查询到的数据封装到vo
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(list);
        return dishVO;
    }

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     * @param dishDTO
     * @return
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        //修改菜品基本信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //删除原有的口味数据

        dishFlavorMapper.deleteByDishId(dish.getId());
        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0)
        {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    @Override
    public void StopOrStartDish(String status, String id) {
        dishMapper.StopOrStartDish(status,id);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> list=dishMapper.list(dish);
        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish D:list) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(D,dishVO);

            List<DishFlavor> byDishId = dishFlavorMapper.getByDishId(D.getId());
            dishVO.setFlavors(byDishId);
            dishVOList.add(dishVO);
       }
        return dishVOList;
    }


}
