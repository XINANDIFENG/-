package com.chenpi.usercenter.service;

import com.chenpi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author a'su's
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-25 16:09:12
*/
public interface UserService extends IService<User> {


    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户 ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    User userlogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param orginUser
     * @return
     */
    User getSafetyUser(User orginUser);
}
