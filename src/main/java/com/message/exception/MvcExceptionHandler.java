package com.message.exception;

import com.alibaba.fastjson.JSONException;
import com.message.http.ResponseResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

//全局异常处理
@ControllerAdvice
@Component
public class MvcExceptionHandler {
    //请求参数格式错误:不带body
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseResult messageNotReadableException(HttpMessageNotReadableException e){
        System.out.println("请求参数解析错误:"+e.getMessage());
        return ResponseResult.invalid_request();
    }

    //请求参数格式错误:json解析错误
    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseBody
    public ResponseResult invalidateRequestException(InvalidRequestException e){
        System.out.println("请求参数错误:"+e.getErrMessage());
        return ResponseResult.invalid_request();
    }

    //请求参数格式错误:json解析错误
    @ExceptionHandler(value = JSONException.class)
    @ResponseBody
    public ResponseResult jsonParseException(JSONException e){
        System.out.println("请求参数错误:"+e.getMessage());
        return ResponseResult.invalid_request();
    }

    //资源不存在
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public  ResponseResult notFoundException(NoHandlerFoundException e){
        System.out.println("资源不存在:"+e.getMessage());
        return ResponseResult.notFound();
    }

    //服务器内部异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult serverException(Exception e){
        System.out.println("服务器异常:"+e.getMessage());
        e.printStackTrace();
        return ResponseResult.internal_server_error();
    }
}