package com.vwmobvoi.oauth2.oauth2server.config;

import com.vwmobvoi.oauth2.oauth2server.service.UserAuthService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @program oauth2-server
 * @description: 应用基础配置
 * @author: liuhx
 * @create: 2020/02/21 11:46
 */
@Configuration
public class AppConfig {
    @Resource
    private ApplicationContext context;

    @Resource
    MappingMongoConverter mappingMongoConverter;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(3)).setReadTimeout(Duration.ofSeconds(30)).build();
    }

    @PostConstruct
    public void init() {
        // 消除 spring-data-mongo 自动映射生成的collection中带有的_class属性
        mappingMongoConverter.setTypeMapper(defaultMongoTypeMapper());
    }

    @Bean
    public DefaultMongoTypeMapper defaultMongoTypeMapper() {
        return new DefaultMongoTypeMapper(null, mappingMongoConverter.getMappingContext());
    }

    @Bean
    public Map<String, UserAuthService> partnerUserServiceMap() {
        Map<String, UserAuthService> services = new HashMap<>();
        Map<String, UserAuthService> beans = context.getBeansOfType(UserAuthService.class);
        beans.forEach((key, value) -> {
            if (value.userType() != "") {
                services.put(value.userType(), value);
            }
        });
        return services;
    }
}
