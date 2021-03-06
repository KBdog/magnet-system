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
        //?????????????????????,??????????????????
        if(count>0){
            utils.setHash(user.getUid().toString(),user);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Account queryById(Integer uid) {
        Account account=null;
        //??????redis???????????????
        LinkedHashMap linkedHashMap=(LinkedHashMap) utils.getHash(uid.toString());
        //???????????????
        if(linkedHashMap!=null){
            //???????????????json?????????
            String jsonString=new JSONObject().toJSONString(linkedHashMap);
            //???json???????????????json??????
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            //?????????????????????
            account = jsonObject.toJavaObject(Account.class);
        }
        //??????????????????
        if(account==null){
            //??????????????????????????????
            account=mapper.queryById(uid);
            //?????????????????????
            utils.setHash(uid.toString(),account);
        }
        return account;
    }
}
