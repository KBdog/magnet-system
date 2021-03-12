package com.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.message.entity.Account;
import com.message.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountService service;
    //查询账户
    @RequestMapping("/validate")
    public boolean validate(@RequestBody String json){
        boolean flag=false;
        JSONObject jsonObject=JSONObject.parseObject(json);
        //json格式化
//        String jsonString= JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteDateUseDateFormat);
        //获取属性
        String accountString = jsonObject.getString("account");
        String passwordString = jsonObject.getString("password");
        Account account=new Account();
        account.setAccount(accountString);
        account.setPassword(passwordString);
        Integer integer = service.validateAccount(account);
        if(integer>0){
            System.out.println("登录成功");
            flag=true;
        }else {
            System.out.println("登录失败");
            flag=false;
        }
        return flag;
    }

    //获取账户信息
    @RequestMapping("/message")
    public Account getAccount(@RequestBody String json){
        JSONObject jsonObject=JSONObject.parseObject(json);
        //获取属性
        String accountString = jsonObject.getString("account");
        String passwordString = jsonObject.getString("password");
        Account account=new Account();
        account.setAccount(accountString);
        account.setPassword(passwordString);
        return service.getAccount(account);
    }
}
