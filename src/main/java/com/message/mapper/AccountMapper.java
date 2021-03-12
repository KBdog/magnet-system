package com.message.mapper;

import com.message.entity.Account;
import com.message.entity.Magnet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    @Select("SELECT count(*) FROM account_table WHERE account=#{account.account} AND password=#{account.password}")
    Integer validateAccount(@Param("account") Account account);
    @Select("SELECT * FROM account_table WHERE account=#{account.account} AND password=#{account.password}")
    Account getAccount(@Param("account")Account account);
    @Select("select* from account_table where authority!=2")
    List<Account> queryAll();
    @Select("select * from account_table where authority!=2 limit #{currentPage},#{pageNum}")
    List<Account> pagingQueryAccount(@Param("currentPage") Integer currentPage, @Param("pageNum") Integer pageNum);
    @Select("select *from account_table " +
            "where account like CONCAT('%',#{key},'%') " +
            "and authority!=2")
    List<Account> queryAccountByKey(@Param("key") String keyword);
    @Insert("insert into account_table(account,password,authority) values (#{account},#{password},#{authority})")
    Integer addUser(Account user);
    @Select("select * from account_table where account=#{username}")
    Account checkExist(@Param("username") String username);
    @Delete("delete from account_table where uid=#{uid}")
    Integer deleteUser(@Param("uid") Integer uid);
    @Update("update account_table " +
            "set account=#{user.account},password=#{user.password},authority=#{user.authority} " +
            "where uid=#{user.uid}")
    Integer updateUser(@Param("user") Account user);
    @Select("select * from account_table where uid=#{uid} and authority!=2")
    Account queryById(Integer uid);
}
