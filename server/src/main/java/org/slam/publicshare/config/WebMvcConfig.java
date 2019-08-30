package org.slam.publicshare.config;

import org.slam.publicshare.interceptor.NoticeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final NoticeInterceptor noticeInterceptor;

    public WebMvcConfig(NoticeInterceptor noticeInterceptor) {
        this.noticeInterceptor = noticeInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("*").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noticeInterceptor).excludePathPatterns("/sign-in", "/sign-up", "/js/**", "/css/**", "/image/**");
    }

}