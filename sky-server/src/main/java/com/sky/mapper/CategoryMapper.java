package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 唐诗杰
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2024-08-10 20:55:56
* @Entity com.sky.Category
*/
@Mapper
public interface CategoryMapper {

   Page<Category> PageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

   void update(Category category);

   @Update("update category set status=#{status} where id=#{id}")
    void StartOrStop(Integer status, Long id);

   @Insert("insert into category (type,name,sort,status,create_time,update_time,create_user,update_user)" +
           "VALUES " +
           "(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

   @Delete("delete from category where id=#{id}")
    void delete(Long id);

    List<Category> list(Integer type);
}




