package com.message.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器
@Component
public class ApiInterceptor implements HandlerInterceptor {
    //在controller前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器...");
        System.out.println("请求链接:"+request.getRequestURL());
        return true;
        //拦截all的请求,并重定向到all/3/6中
//        if("http://localhost:8081/queryMagnet/all".equals(request.getRequestURL().toString())){
//            response.sendRedirect("/queryMagnet/all/3/6");
//            return false;
//        }else {
//            return true;
//        }
    }

    //在controller后调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle:访问controller完毕");
    }

    //整个过程结束后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion:整个过程完毕");
    }
}
