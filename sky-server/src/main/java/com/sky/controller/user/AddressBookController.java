package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/16 10:25
 **/
@Api(tags = "C端地址簿接口")
@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list()
    {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    /**
     * 新增地址
     * @return
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result add(@RequestBody AddressBook addressBook)
    {
        addressBookService.add(addressBook);
        return Result.success();
    }


    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault()
    {
        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");

    }


    /**
     * 根据id修改地址
     * @return
     */
    @PutMapping()
    @ApiOperation("根据id修改地址")
    public Result update(@RequestBody AddressBook addressBook)
    {
        addressBookService.updateById(addressBook);
        return Result.success();
    }


    /**
     * 根据id删除地址
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result delete(Long id)
    {
        addressBookService.deleteById(id);
        return null;
    }


    /**
     * 根据id查询地址
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddressById(@PathVariable Long id)
    {
        AddressBook addressBook=addressBookService.getAddressById(id);
        return Result.success(addressBook);
    }

    /**
     * 设置默认地址
     * @return
     */
    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public Result SetDefault(@RequestBody AddressBook addressBook)
    {
        addressBookService.setDefualt(addressBook);
        return Result.success();
    }

}
