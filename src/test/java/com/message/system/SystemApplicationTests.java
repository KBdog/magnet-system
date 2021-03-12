package com.message.system;

import com.message.entity.Magnet;
import com.message.service.MagnetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SystemApplicationTests {

    @Autowired
    private MagnetService service;

    @Test
    void contextLoads() {
    }

    @Test
    void testAdd(){
        Magnet magnet=new Magnet();
        magnet.setName("test");
        magnet.setMagnet("test");
        Integer num = service.addMagnet(magnet);
        if(num>0){
            System.out.println(num);
        }else {
            System.out.println("插入失败");
        }

    }

    @Test
    void testDelete(){
        Magnet magnet=new Magnet();
        magnet.setName("test");
        magnet.setMagnet("test");
        Integer num = service.deleteMagnet(magnet);
        if(num>0){
            System.out.println(num);
        }else {
            System.out.println("删除失败");
        }
    }

    @Test
    void testUpdate(){
        Magnet oldMagnet=new Magnet();
        Magnet newMagnet=new Magnet();
        oldMagnet.setName("test");
        oldMagnet.setMagnet("test3");
        newMagnet.setName("test_new_name");
        newMagnet.setMagnet("test_new_magnet");
        System.out.println(service.updateMagnet(oldMagnet,newMagnet));
    }

    @Test
    void testTimeReport(){
        String start="asdas";
        String end="asdasdas";
        List<Magnet> magnets = service.queryTimeReport(start, end);
        for(Magnet magnet:magnets){
            System.out.println(magnet.getName()+","+magnet.getTime());
        }
    }

    @Test
    void toExcel(){
        //2020年11月7日01:14:00已向项目中导入poi
    }





    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void testRedis(){
        //set(key,value,time,time_type)
        redisTemplate.opsForValue().set("key","1231231",10, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("key"));
    }
}
