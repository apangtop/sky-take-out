package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void Insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    /**
     * 根据主键查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);


    /**
     * 根据id主键删除某个菜品
     * @param id
     */
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);


    /**
     * 根据菜品id集合批量删除菜品
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改菜品基本信息
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    @Update("update dish set status=#{status} where id=#{id}")
    void StopOrStartDish(String status, String id);

    @Select("select * from dish where category_id=#{categoryId} and status=#{status}")
    List<Dish> list(Dish dish);
}
