package com.vwmobvoi.oauth2.oauth2server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.AuthPageVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenReqVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenRespVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.TokenVo;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerUserService;
import com.vwmobvoi.oauth2.oauth2server.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program oauth2-server
 * @description: token验证重置发放实现类
 * @author: liuhx
 * @create: 2020/02/25 18:11
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Resource
    public StringRedisTemplate stringRedisTemplate;
    @Value("${key.visit-id-name}")
    private String visitIdKey;
    @Value("${key.code}")
    private String codeKey;
    @Value("${key.refresh-token}")
    private String refreshTokenKey;
    @Value("${key.token}")
    private String tokenKey;
    @Resource
    PartnerUserService partnerUserService;

    /**
     * token 颁发和重置
     *
     * @param tokenReqVo
     * @return
     */
    @Override
    public TokenRespVo token(TokenReqVo tokenReqVo) throws InvocationTargetException, IllegalAccessException {
        AuthPageVo authPageVo = new AuthPageVo();
        TokenVo tokenVo;
        String oldToken = "";
        String oldRefreshToken = "";
        /** 刷新token **/
        if ("refresh_token".equals(tokenReqVo.getGrant_type())) {
            /** 查看充值tokenkey是否有 **/
            if (stringRedisTemplate.hasKey(refreshTokenKey + tokenReqVo.getRefresh_token())) {
                // 查看 对应token
                log.info(tokenKey + tokenReqVo.getRefresh_token());
                String tokenString = stringRedisTemplate.opsForValue()
                        .get(refreshTokenKey + tokenReqVo.getRefresh_token());
                // 根据token查询相关用户以及供应商信息
                String tokenJson = stringRedisTemplate.opsForValue().get(tokenKey + tokenString);
                tokenVo = JSON.parseObject(tokenJson, TokenVo.class);
                authPageVo.setUserType(tokenVo.getUserType());
                authPageVo.setUserId(tokenVo.getUserId());
                authPageVo.setClient_id(tokenVo.getClientId());
                PartnerUser partnerUser = partnerUserService.findByClientId(tokenVo.getClientId());
                authPageVo.setUserSecret(partnerUser.getClientSecret());
                oldToken = tokenString;
                oldRefreshToken = tokenVo.getRefreshToken();
            } else {
                return TokenRespVo.builder().error("refresh_token verify failed").build();
            }
            /** 根据code获取token **/
        } else if ("authorization_code".equals(tokenReqVo.getGrant_type())) {
            log.info(codeKey + tokenReqVo.getCode());
            if (stringRedisTemplate.hasKey(codeKey + tokenReqVo.getCode())) {
                String code = tokenReqVo.getCode();
                String visitId = stringRedisTemplate.opsForValue().get(codeKey + code);
                Map map = stringRedisTemplate.boundHashOps(visitIdKey + visitId).entries();
                BeanUtils.populate(authPageVo, map);
            } else {
                return TokenRespVo.builder().error("code verify failed or out of time").build();
            }
        } else {
            log.error("GrantType不正确{}", tokenReqVo.getGrant_type());
        }
        if (!authPageVo.getUserSecret().equals(tokenReqVo.getClient_secret())) {
            return TokenRespVo.builder().error("secret不正确").build();
        }
        tokenVo = TokenVo.builder().userId(authPageVo.getUserId()).clientId(authPageVo.getClient_id())
                .userType(authPageVo.getUserType()).accessToken(UUID.randomUUID().toString().replaceAll("-", ""))
                .refreshToken(UUID.randomUUID().toString().replaceAll("-", "")).build();
        stringRedisTemplate.opsForValue().set(tokenKey + tokenVo.getAccessToken(),
                JSONObject.toJSON(tokenVo).toString(), 3, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey + tokenVo.getRefreshToken(), tokenVo.getAccessToken());
        if (!"".equals(oldToken) && !"".equals(oldRefreshToken)) {
            stringRedisTemplate.delete(tokenKey + oldToken);
            stringRedisTemplate.delete(refreshTokenKey + oldRefreshToken);
        }
        return TokenRespVo.builder().accessToken(tokenVo.getAccessToken()).expiresIn(TimeUnit.DAYS.toSeconds(3))
                .refreshToken(tokenVo.getRefreshToken()).tokenType("bearer").build();
    }

    /**
     * 根据token验证用户
     *
     * @param token
     * @return
     */
    @Override
    public TokenVo getToken(String token) {
        String tokenJson = stringRedisTemplate.opsForValue().get(tokenKey + token);
        TokenVo tokenVo = JSONObject.parseObject(tokenJson, TokenVo.class);
        return tokenVo;
    }
}
