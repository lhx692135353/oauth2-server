package com.vwmobvoi.oauth2.oauth2server.service;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenReqVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenRespVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenVo;

import java.lang.reflect.InvocationTargetException;

/**
 * token 验证、重置发放
 */
public interface TokenService {
    /**
     * token 办法和重置
     *
     * @param tokenReqVo
     * @return
     */
    TokenRespVo token(TokenReqVo tokenReqVo) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据token验证用户
     *
     * @param token
     * @return
     */
    TokenVo getToken(String token);
}
