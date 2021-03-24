package com.message.controller;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.annotation.PassLogin;
import com.message.entity.Magnet;
import com.message.httpresponse.ResponseResult;
import com.message.service.MagnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/queryMagnet")
public class MagnetController {
    @Autowired
    private MagnetService service;

    //查询所有磁力链接
    @PassLogin
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    private ResponseResult queryAllMagnet(){
        List<Magnet> magnetList=service.queryAllMagnet();
        return ResponseResult.ok(magnetList);
    };
    //模糊查询
    //模糊查询要检查token,不加passlogin注释
    @RequestMapping("/keyword/")
    private ResponseResult queryMagnetByKey(@RequestBody String jsonString) throws JsonProcessingException {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String keyword = jsonObject.getString("keyword");
        System.out.println("keyword:"+keyword);
        List<Magnet> magnetList=service.queryMagnetByKey(keyword);
        return ResponseResult.ok(magnetList);
    };
    //分页查询
    @PassLogin
    @RequestMapping("/all/{currentPage}/{pageNum}")
    private ResponseResult pagingQueryMagnet(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageNum") Integer pageNum){
        List<Magnet>magnetList=service.pagingQueryMagnet((currentPage-1)*pageNum,pageNum);
        return ResponseResult.ok(magnetList);
    }
    //添加磁力
    @PassLogin
    @RequestMapping("/add_magnet/")
    private boolean addMagnet(@RequestBody String jsonString) throws JsonProcessingException {
        boolean flag=false;
        //解析json字符串
        ObjectMapper jsonObject=new ObjectMapper();
        //解析为Magnet对象
        Magnet magnet=jsonObject.readValue(jsonString,Magnet.class);
        Integer count;
        try {
            count=service.addMagnet(magnet);
            if(count>0){
                System.out.println("添加成功:"+magnet.getName());
                flag=true;
            }else {
                System.out.println("添加失败:"+magnet.getName());
                flag=false;
            }

        }catch (Exception e){
            //异常返回
            return flag;
        }finally {
            return flag;
        }
    }
    //删除磁力
    @PassLogin
    @RequestMapping("/delete_magnet/")
    private boolean deleteMagnet(@RequestBody String jsonString) throws JsonProcessingException {
        boolean flag=false;
        //解析json字符串
        ObjectMapper jsonObject=new ObjectMapper();
        //解析为Magnet对象
        Magnet magnet=jsonObject.readValue(jsonString,Magnet.class);
        Integer count=service.deleteMagnet(magnet);
        if(count>0){
            System.out.println("成功删除:"+magnet.getName());
            flag=true;
        }else {
            System.out.println("删除失败:"+magnet.getName());
            flag=false;
        }
        return flag;
    }
    //编辑磁力
    @PassLogin
    @RequestMapping("/update_magnet/")
    private boolean updateMagnet(@RequestBody String jsonObject) throws JsonProcessingException {
        boolean flag=false;
        //解析json数组（含多个对象）
        ObjectMapper mapper=new ObjectMapper();
        Magnet[] magnets = mapper.readValue(jsonObject, Magnet[].class);
        Integer count=service.updateMagnet(magnets[0],magnets[1]);
        if(count>0){
            System.out.println("成功修改！\n旧资源信息:"+magnets[0].toString()
                    +"\n新资源信息:"+magnets[1].toString());
            flag=true;
        }else {
            System.out.println("修改失败！");
            flag=false;
        }
        return flag;
    }

    //查询某个时间段收录的磁力
    @PassLogin
    @RequestMapping("/query_TimeReport/")
    private ResponseResult queryTimeReport(@RequestBody Map<String,String> jsonTimeRange) throws JsonProcessingException {
        String []time=new String[2];
        String start="";
        String end="";
        int i=0;
        for (Map.Entry<String, String> tmp:jsonTimeRange.entrySet()) {
            time[i]=tmp.getValue();
            i++;
        }
        start=time[0]+" 00:00:00";
        end=time[1]+" 23:59:59";
        System.out.println("起始时间:"+start+"\n"+"结束时间:"+end);
        List<Magnet> magnetList=service.queryTimeReport(start,end);
        return ResponseResult.ok(magnetList);
    }

    //下载excel
    @PassLogin
    @RequestMapping("/downloadExcel")
    private void downloadExcel(@RequestBody String jsonString,HttpServletResponse response) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        Magnet[] magnets = mapper.readValue(jsonString, Magnet[].class);
        ServletOutputStream outputStream=response.getOutputStream();
        String titles[]={"资源名","磁力链接","收录时间"};
        boolean flag=service.downloadExcel(magnets,outputStream,titles);
        if(flag){
            System.out.println("下载成功");
        }else {
            System.out.println("下载失败");
        }


        //下载
//        String filePath="C:/Users/Lenovo/Desktop/test/bg1.jpg";
//        File file=new File(filePath);
//        byte []cache=new byte[1024];
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
//        //各种流
//        FileInputStream inputStream=null;
//        BufferedInputStream bufferedInputStream=null;
//        ServletOutputStream outputStream=null;
//        try {
//            //把文件读入输入流
//            inputStream=new FileInputStream(file);
//            //把输入流读入输入缓冲流
//            bufferedInputStream=new BufferedInputStream(inputStream);
//            //获取输出流
//            outputStream=response.getOutputStream();
//            //把缓冲流中数据读入缓存
//            int i=bufferedInputStream.read(cache);
//            while(i!=-1){
//                outputStream.write(cache,0,cache.length);
//                outputStream.flush();
//                i=bufferedInputStream.read(cache);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                //关闭流
//                bufferedInputStream.close();
//                outputStream.close();
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }

    //每月收录磁力数
    @PassLogin
    @RequestMapping("/count")
    private Integer countMagnet(@RequestParam("start")String start,@RequestParam("end")String end){
        return service.countMagnet(start,end);
    }



    //test
    @PassLogin
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    private void test(@RequestBody Magnet receive){
        Magnet magnet=receive;
        System.out.println(magnet.toString());
    }
}
