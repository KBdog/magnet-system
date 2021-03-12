package com.message.service;

import com.message.entity.Account;
import com.message.entity.Magnet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    Integer validateAccount(Account account);
    Account getAccount(Account account);
    List<Account> queryAll();
    List<Account> pagingQueryAccount(Integer currentPage, Integer pageNum);
    List<Account> queryAccountByKey(String keyword);
    Integer addUser(Account user);
    Account checkExist(String username);
    Integer deleteUser(Integer uid);
    Integer updateUser(Account user);
    Account queryById(Integer uid);
}
