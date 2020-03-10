package com.vwmobvoi.oauth2.oauth2server.config;

import com.vwmobvoi.oauth2.oauth2server.config.filter.DurationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program oauth2-server
 * @description: Web基础配置
 * @author: liuhx
 * @create: 2020/02/21 11:28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * 日志记录过滤器
     *
     * @return 日志记录过滤器
     */
    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(1024);
        return filter;
    }

    /**
     * 请求计时过滤器
     *
     * @return 请求计时过滤器
     */
    @Bean
    public DurationFilter durationFilter() {
        return new DurationFilter();
    }

    /**
     * 添加拦截器
     *
     * @param registry 　拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addStatusController("", HttpStatus.OK);
    }
}
