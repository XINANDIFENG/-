package com.chenpi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenpi.usercenter.model.domain.User;
import com.chenpi.usercenter.model.domain.request.UserLoginRequest;
import com.chenpi.usercenter.model.domain.request.UserRegisterRequest;
import com.chenpi.usercenter.service.UserService;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chenpi.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.chenpi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author ChenPi
 * @date 2024-03-26 11:20
 * @description:
 * @version:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userlogin(@RequestBody UserLoginRequest userloginRequest, HttpServletRequest request) {
        if (userloginRequest == null) {
            return null;
        }
        String userAccount = userloginRequest.getUserAccount();
        String userPassword = userloginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userlogin(userAccount, userPassword,request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request){
        if (!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request){
        if (isAdmin(request)){
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
