package com.vwmobvoi.oauth2.oauth2server.service;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.AuthPageVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.LoginResp;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.SenderVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.VisitVo;

public interface PartnerAuthService {
    /**
     * 客户端发起申请权限
     * @return
     */
    VisitVo applyAuth(AuthPageVo authPageVo);


    /**
     * 用户登录
     */
    SenderVo<LoginResp> userLogin(String visitId, String json);
}
