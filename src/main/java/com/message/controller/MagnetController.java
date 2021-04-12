package com.message.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.annotation.PassLogin;
import com.message.entity.Magnet;
import com.message.exception.InvalidRequestException;
import com.message.http.HttpEnum;
import com.message.http.ResponseResult;
import com.message.service.MagnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    }
    //模糊查询
    //模糊查询要检查token,不加passlogin注释
    @RequestMapping(value="/keyword",method = RequestMethod.POST)
    private ResponseResult queryMagnetByKey(@RequestBody String jsonString, HttpServletRequest request){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String keyword = jsonObject.getString("keyword");
        Integer uid=jsonObject.getInteger("uid");
        if(jsonObject.size()!=2||keyword==null){
            //抛请求参数格式错误异常
            throw new InvalidRequestException(HttpEnum.INVALID_REQUEST);
        }
        Integer subjectUid=Integer.parseInt((String) request.getAttribute("subject"));
        //对发送token的用户uid进行验证，防止其他用户冒用token
        if(subjectUid!=uid){
            System.out.println("用户uid跟token对应不上:"+"用户uid-"+uid+"|token_uid-"+subjectUid);
            return ResponseResult.unauthorized();
        }
        System.out.println("keyword:"+keyword);
        List<Magnet> magnetList=service.queryMagnetByKey(keyword);
        return ResponseResult.ok(magnetList);
    }
    //分页查询
    @PassLogin
    @RequestMapping(value="/all/{currentPage}/{pageNum}",method = RequestMethod.GET)
    private ResponseResult pagingQueryMagnet(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageNum") Integer pageNum){
        List<Magnet>magnetList=service.pagingQueryMagnet((currentPage-1)*pageNum,pageNum);
        return ResponseResult.ok(magnetList);
    }
    //添加磁力
    @PassLogin
    @RequestMapping(value = "/add_magnet",method = RequestMethod.POST)
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
    @RequestMapping(value="/delete_magnet",method = RequestMethod.DELETE)
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
    //批量删除磁力
    @PassLogin
    @RequestMapping(value = "/batch/delete",method = RequestMethod.DELETE)
    private ResponseResult batchDeleteMagnet(@RequestBody String jsonString){
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        List<String> magnetNameList=new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            magnetNameList.add(jsonObject.getString("name"));
        }
        Integer count=service.batchDeleteMagnet(magnetNameList);
        System.out.println("删除了"+count+"条磁力信息");
        Map<String ,Object>message=new HashMap<>();
        message.put("total",count);
        if(count>0){
            message.put("result",true);
        }else {
            message.put("result",false);
        }
        return ResponseResult.ok(message);
    }
    //编辑磁力
    @PassLogin
    @RequestMapping(value="/update_magnet",method = RequestMethod.PUT)
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
    @RequestMapping(value = "/query_TimeReport",method = RequestMethod.POST)
    private ResponseResult queryTimeReport(@RequestBody Map<String,String> jsonTimeRange){
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
    @RequestMapping(value="/downloadExcel",method = RequestMethod.POST)
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
    }

    //每月收录磁力数
    @PassLogin
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    private Integer countMagnet(@RequestParam("start")String start,@RequestParam("end")String end){
        return service.countMagnet(start,end);
    }
}
