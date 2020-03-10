package com.vwmobvoi.oauth2.oauth2server.service;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.UserAuthRespVo;

public interface UserAuthService {

    UserAuthRespVo userAuth(String userType , String json);

    default String userType(){
        return "";
    }
}
