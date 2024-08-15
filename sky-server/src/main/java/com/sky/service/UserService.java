package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/8/14 23:09
 **/
public interface UserService {
    /**
     * 微信登录功能
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
