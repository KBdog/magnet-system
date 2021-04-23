package com.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.message.entity.Account;
import com.message.mapper.AccountMapper;
import com.message.service.AccountService;
import com.message.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Primary
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private RedisUtils utils;
    @Override
    @Transactional
    public Integer validateAccount(Account account) {
        return mapper.validateAccount(account);
    }

    @Override
    @Transactional
    public Account getAccount(Account account) {
        return mapper.getAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> queryAll() {
        return mapper.queryAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> pagingQueryAccount(Integer currentPage, Integer pageNum) {
        return mapper.pagingQueryAccount(currentPage,pageNum);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> queryAccountByKey(String keyword) {
        return mapper.queryAccountByKey(keyword);
    }

    @Override
    @Transactional
    public Integer addUser(Account user) {
        Integer result=-1;
        try {
            result=mapper.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Account checkExist(String username) {
        return mapper.checkExist(username);
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer uid) {
        Integer count=mapper.deleteUser(uid);
        if(count>0){
            System.out.println(utils.deleteHash(uid.toString()));
        }
        return count;
    }

    @Override
    @Transactional
    public Integer updateUser(Account user) {
        Integer count=mapper.updateUser(user);
        //数据库修改成功,缓存也作修改
        if(count>0){
            utils.setHash(user.getUid().toString(),user);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Account queryById(Integer uid) {
        Account account=null;
        //先从redis中获取数据
        LinkedHashMap linkedHashMap=(LinkedHashMap) utils.getHash(uid.toString());
        //获取到数据
        if(linkedHashMap!=null){
            //哈希集合转json字符串
            String jsonString=new JSONObject().toJSONString(linkedHashMap);
            //把json字符串转成json对象
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            //把对象转成实体
            account = jsonObject.toJavaObject(Account.class);
        }
        //获取不到数据
        if(account==null){
            //缓存中为空查询数据库
            account=mapper.queryById(uid);
            //把结果写入缓存
            utils.setHash(uid.toString(),account);
        }
        return account;
    }
}
