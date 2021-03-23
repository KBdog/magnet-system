package com.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.annotation.PassLogin;
import com.message.entity.Account;
import com.message.entity.Magnet;
import com.message.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/manage")
public class UsersController {
    @Autowired
    private AccountService service;
    //查询所有用户
    @PassLogin
    @RequestMapping("/all")
    private List<Account>queryAllUser(){
        return service.queryAll();
    }
    //分页查询
    @PassLogin
    @RequestMapping("/all/{currentPage}/{pageNum}")
    private List<Account> pagingQueryMagnet(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageNum") Integer pageNum){
        return service.pagingQueryAccount((currentPage-1)*pageNum,pageNum);
    }
    //模糊查询
    @PassLogin
    @RequestMapping("/keyword")
    private List<Account> queryMagnetByKey(@RequestBody Map<String,String> jsonKeyword) throws JsonProcessingException {
        String keyword="";
        for (Map.Entry<String, String> tmp:jsonKeyword.entrySet()) {
            keyword=tmp.getValue();
        }
        System.out.println("keyword:"+keyword);
        return service.queryAccountByKey(keyword);
    }
    //添加用户
    @PassLogin
    @RequestMapping("/add_user")
    private boolean addUser(@RequestBody String jsonString) throws JsonProcessingException {
        boolean flag=false;
        JSONObject jsonObject= JSON.parseObject(jsonString);
        Account account=new Account();
        account.setAccount(jsonObject.getString("username"));
        account.setPassword(jsonObject.getString("password"));
        account.setAuthority(Integer.parseInt(jsonObject.getString("profile")));
        Integer count;
        try {
            count=service.addUser(account);
            if(count>0){
                System.out.println("添加成功:"+account.getAccount());
                flag=true;
            }else {
                System.out.println("添加失败:"+account.getAccount());
                flag=false;
            }
        }catch (Exception e){
            //异常返回
            return flag;
        }finally {
            return flag;
        }
    }
    //查看用户是否存在
    @PassLogin
    @RequestMapping("/check")
    private boolean checkExist(@RequestParam("username")String username){
        boolean flag=true;
        Account account = service.checkExist(username);
        if(account==null){
            flag=false;
        }
        return flag;
    }
    //根据uid删除用户
    @PassLogin
    @RequestMapping("/delete")
    private boolean deleteUser(@RequestParam("uid") Integer uid){
        boolean flag=false;
        Integer count;
        count=service.deleteUser(uid);
        if(count>0){
            flag=true;
            System.out.println("删除成功:"+uid);
        }
        return flag;
    }
    //更新用户
    @PassLogin
    @RequestMapping("/update")
    private boolean updateUser(@RequestBody String jsonString){
        boolean flag=false;
        JSONObject jsonObject=JSON.parseObject(jsonString);
        Account account=new Account();
        account.setUid(Integer.parseInt(jsonObject.getString("uid")));
        account.setAccount(jsonObject.getString("account"));
        account.setPassword(jsonObject.getString("password"));
        account.setAuthority(Integer.parseInt(jsonObject.getString("authority")));
        Integer count=service.updateUser(account);
        if(count>0){
            flag=true;
        }
        return flag;
    }
    //根据id查用户
    @PassLogin
    @RequestMapping("/id")
    private Account queryById(@RequestParam("uid")Integer id){
        return service.queryById(id);
    }
}
