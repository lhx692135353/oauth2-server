package com.vwmobvoi.oauth2.oauth2server.service.impl;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.UserAuthRespVo;
import com.vwmobvoi.oauth2.oauth2server.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @program oauth2-server
 * @description: 用户验证例子
 * @author: liuhx
 * @create: 2020/02/25 14:41
 */
@Service
public class ExampleUserAuthServiceImpl implements UserAuthService {
    @Override
    public UserAuthRespVo userAuth(String userType, String json) {
        return UserAuthRespVo.builder().code(1).userId("testUserId").build();
    }

    @Override
    public String userType() {
        return "example";
    }
}
