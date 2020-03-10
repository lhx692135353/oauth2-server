package com.vwmobvoi.oauth2.oauth2server.service.impl;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.UserAuthRespVo;
import com.vwmobvoi.oauth2.oauth2server.service.UserAuthService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program oauth2-server
 * @description: 加载用户认证类
 * @author: liuhx
 * @create: 2020/02/25 14:30
 */

@Service
@Primary
public class UserAuthSupplierService implements UserAuthService {
    @Resource
    private Map<String,UserAuthService> partnerUserServiceMap;


    @Override
    public UserAuthRespVo userAuth(String userType, String json) {
        return partnerUserServiceMap.get(userType).userAuth(userType,json);
    }


}
