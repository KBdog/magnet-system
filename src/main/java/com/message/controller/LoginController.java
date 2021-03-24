package com.message.controller;

import com.alibaba.fastjson.JSONObject;
import com.message.annotation.PassLogin;
import com.message.entity.Account;
import com.message.http.ResponseResult;
import com.message.service.AccountService;
import com.message.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AccountService service;

    //登录并签发token
    @PassLogin
    @PostMapping("/validate")
    public ResponseResult login(@RequestBody String jsonString, HttpServletResponse response){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String username = jsonObject.getString("account");
        String password = jsonObject.getString("password");
        Account user=new Account();
        user.setAccount(username);
        user.setPassword(password);
        Integer count = service.validateAccount(user);
        if(count>0){
            Account account = service.getAccount(user);
            System.out.println("登陆成功");
            //用uid签发token
            String token=jwtUtils.createToken(account.getUid().toString());
            response.setHeader(jwtUtils.getHeader(),token);
            Map<String,String>message=new HashMap<>();
            message.put("isLogin","true");
            message.put("uuid",account.getUid().toString());
            message.put("message","登录成功");
            message.put("token",response.getHeader(jwtUtils.getHeader()));
            return ResponseResult.ok(message);
        }else {
            System.out.println("登陆失败");
            Map<String,String>message=new HashMap<>();
            message.put("isLogin","false");
            message.put("message","登录失败");
//            return ResponseResult.ok(message);
            return ResponseResult.unauthorized(message);
        }
    }




    //登录完成获取账户信息
    @PassLogin
    @RequestMapping("/message")
    public ResponseResult getAccount(@RequestBody String json){
        JSONObject jsonObject=JSONObject.parseObject(json);
        //获取属性
        String accountString = jsonObject.getString("account");
        String passwordString = jsonObject.getString("password");
        Account account=new Account();
        account.setAccount(accountString);
        account.setPassword(passwordString);
        Account resultAccount = service.getAccount(account);
        return ResponseResult.ok(resultAccount);
    }
}
