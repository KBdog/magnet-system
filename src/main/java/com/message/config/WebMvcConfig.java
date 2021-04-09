package com.message.config;

import com.message.interceptor.ApiInterceptor;
import com.message.interceptor.TokenInterceptor;
import com.message.service.MagnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//webmvc配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    //拦截器设置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }

//    @Bean
//    public TokenInterceptor tokenInterceptor(){
//        return new TokenInterceptor();
//    }

    //跨域设置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许证书
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

}
