package com.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.message.annotation.PassLogin;
import com.message.entity.Account;
import com.message.http.ResponseResult;
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
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    private ResponseResult queryAllUser(){
        List<Account>accountList=service.queryAll();
        return ResponseResult.ok(accountList);
    }
    //分页查询
    @PassLogin
    @RequestMapping(value = "/all/{currentPage}/{pageNum}",method = RequestMethod.GET)
    private ResponseResult pagingQueryMagnet(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageNum") Integer pageNum){
        List<Account>accountList=service.pagingQueryAccount((currentPage-1)*pageNum,pageNum);
        return ResponseResult.ok(accountList);
    }
    //模糊查询
    @PassLogin
    @RequestMapping(value = "/keyword",method = RequestMethod.POST)
    private ResponseResult queryMagnetByKey(@RequestBody Map<String,String> jsonKeyword) throws JsonProcessingException {
        String keyword="";
        for (Map.Entry<String, String> tmp:jsonKeyword.entrySet()) {
            keyword=tmp.getValue();
        }
        System.out.println("keyword:"+keyword);
        List<Account>accountList=service.queryAccountByKey(keyword);
        return ResponseResult.ok(accountList);
    }
    //根据id查用户
    @PassLogin
    @RequestMapping(value="/id",method = RequestMethod.GET)
    private Account queryById(@RequestParam("uid")Integer id){
        return service.queryById(id);
    }



    //添加用户
    @PassLogin
    @RequestMapping(value = "/add_user",method = RequestMethod.POST)
    private boolean addUser(@RequestBody String jsonString) {
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
            e.printStackTrace();
            //异常返回
            return flag;
        }finally {
            return flag;
        }
    }
    //查看用户是否存在
    @PassLogin
    @RequestMapping(value="/check",method = RequestMethod.GET)
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
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
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
    @RequestMapping(value = "/update",method = RequestMethod.POST)
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

}
