package com.message.exception;

import com.alibaba.fastjson.JSONException;
import com.message.http.ResponseResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

//全局异常处理
@ControllerAdvice
@RestController
public class WebMvcExceptionHandler {
    //整合请求方式、请求方法体、请求参数的异常
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,InvalidRequestException.class,
            JSONException.class, MissingServletRequestParameterException.class})
    public ResponseResult paramError(Exception e){
        System.out.println("请求方式或参数错误:"+e.getMessage());
        return ResponseResult.invalid_request();
    }


    //资源不存在
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public  ResponseResult notFoundHandler(NoHandlerFoundException e){
        System.out.println("资源不存在:"+e.getMessage());
        return ResponseResult.notFound();
    }

    //其他情况:服务器内部异常
    @ExceptionHandler(value = Exception.class)
    public ResponseResult serverHandler(Exception e){
        System.out.println("服务器异常:"+e.getMessage());
        e.printStackTrace();
        return ResponseResult.internal_server_error();
    }
}
