package com.vwmobvoi.oauth2.oauth2server.controller;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenReqVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenRespVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenVo;
import com.vwmobvoi.oauth2.oauth2server.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program oauth2-server
 * @description: token验证 token获取和token重置
 * @author: liuhx
 * @create: 2020/02/25 17:36
 */
@RestController
@Slf4j
public class TokenController {

    @Resource
    TokenService tokenService;

    @PostMapping("token")
    public TokenRespVo token(TokenReqVo tokenReqVo) {
        TokenRespVo tokenRespVo = new TokenRespVo();
        try {
            tokenRespVo = tokenService.token(tokenReqVo);
        } catch (Exception e) {
            log.error("token获取重置错误", e);
            e.printStackTrace();
            tokenRespVo.setError("server error");
        }
        return tokenRespVo;
    }

    @GetMapping("checkToken")
    public TokenVo checkToken(String token) {
        TokenVo tokenVo = tokenService.getToken(token);
        return tokenVo;
    }
}
