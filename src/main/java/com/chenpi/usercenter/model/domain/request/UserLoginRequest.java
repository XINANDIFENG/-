package com.chenpi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ChenPi
 * @date 2024-03-26 14:34
 * @description: 用户注册请求体
 * @version:
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 4753132227742427395L;

    private String userAccount;
    private String userPassword;

}
