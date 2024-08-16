package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/16 10:33
 **/
public interface AddressBookService {

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址
     * @param addressBook
     */
    void add(AddressBook addressBook);

    /**
     *  查询默认地址
     * @param defaultAddress
     * @return
     */


    /**
     * 设置为默认地址
     * @param id
     */
    void setDefualt(AddressBook addressBook);

    /**
     * 根据id修改地址
     * @param addressBook
     */
    void updateById(AddressBook addressBook);

    /**
     * 根据id删除地址
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    AddressBook getAddressById(Long id);
}
