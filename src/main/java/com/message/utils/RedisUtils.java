package com.message.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

//redis工具类
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String,Object>redisTemplate;
    //向redis中写入缓存
    public void setHash(String key, Object obj) {
        redisTemplate.opsForValue().set(key,obj,5,TimeUnit.MINUTES);
    }
    //从redis中取出缓存
    public Object getHash(String key) {
        Object obj=redisTemplate.opsForValue().get(key);
        if(obj==null){
            System.out.println("redis中不存在该对象,key:"+key);
        }else {
            System.out.println("redis中存在该对象,key:"+key);
        }
        return obj;
    }
    //从redis中删除缓存
    public boolean deleteHash(String key){
        return redisTemplate.delete(key);
    }

//    public void setHash(String name, String key, Object obj) {
//        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
//        hash.put(name+"_"+key, key, obj);
//        //设置过期时间10分钟
//        redisTemplate.expire(name+"_"+key,10, TimeUnit.MINUTES);
//    }
//
//    public Object getHash(String name, String key) {
//        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
//        Object obj = hash.get(name, key);
//        if(obj==null){
//            System.out.println("redis中没有该对象");
//        }else {
//            System.out.println("redis中存在该对象");
//        }
//        return obj;
//    }



}
