package com.message.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 账号密码
 * uid:用户id
 * account:用户名
 * password:密码
 * authority:权限
 */
@Data
public class Account implements Serializable {
    private Integer uid;
    private String account;
    private String password;
    private Integer authority;
}
