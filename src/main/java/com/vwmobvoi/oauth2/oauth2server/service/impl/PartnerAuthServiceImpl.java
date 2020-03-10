package com.vwmobvoi.oauth2.oauth2server.service.impl;

import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.*;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerAuthService;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerUserService;
import com.vwmobvoi.oauth2.oauth2server.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program oauth2-server
 * @description: 供应商权限申请实现类
 * @author: liuhx
 * @create: 2020/02/21 16:58
 */
@Service
@Slf4j
public class PartnerAuthServiceImpl implements PartnerAuthService {
    @Resource
    UserAuthService userAuthService;

    @Resource
    public StringRedisTemplate stringRedisTemplate;
    @Value("${key.visit-id-name}")
    private String visitId;
    @Value("${key.code}")
    private String code;

    @Resource
    PartnerUserService partnerUserService;

    /**
     * 客户端发起申请权限
     *
     * @param authPageVo
     * @return
     */
    @Override
    public VisitVo applyAuth(AuthPageVo authPageVo) {
        VisitVo visitVo = new VisitVo();
        try {
            /**根据clientid  找合作商所有信息 **/
            PartnerUser partnerUser = partnerUserService.findByClientId(authPageVo.getClient_id());
            if (partnerUser == null) {
                visitVo.setMessage("未找到该合作商");
                return visitVo;
            }
            String url = URLDecoder.decode(authPageVo.getRedirect_uri());
            /**生成visitId**/
            String visitID = UUID.randomUUID().toString();
            authPageVo.setVisitId(visitID);
            authPageVo.setRedirect_uri(url);
            authPageVo.setUserType(partnerUser.getUserType());
            authPageVo.setUserSecret(partnerUser.getClientSecret());
            Map<String, String> map = BeanUtils.describe(authPageVo);
            /**存储本次访问信息**/
            stringRedisTemplate.boundHashOps(visitId + visitID).putAll(map);
            /**设置20分钟过期**/
            stringRedisTemplate.expire(visitId + visitID,20,TimeUnit.MINUTES);
            visitVo.setVisitId(visitID);
            visitVo.setAuthPageName(partnerUser.getAuthPageName());
        } catch (Exception e) {
            log.error("服务错误：{}", e);
            e.printStackTrace();
            visitVo.setMessage("服务内错误");
        }
        return visitVo;
    }

    /**
     * 用户登录
     *
     * @param visitID
     * @param json
     */
    @Override
    public SenderVo<LoginResp> userLogin(String visitID, String json) {
        //根据visitid 找到该服务请求时的所有参数
        Map map = stringRedisTemplate.opsForHash().entries(visitId + visitID);
        SenderVo<LoginResp> senderVo = new SenderVo<>();
        if (map.size() == 0) {
            senderVo.setStatus("300");
            senderVo.setMessage("未找到该访问id 请重新进入认证界面");
            return senderVo;
        }
        AuthPageVo authPageVo = new AuthPageVo();
        try {
            //map转换为bean
            BeanUtils.populate(authPageVo, map);
            //根据鉴权用户的type找到对应的service 进行鉴权
            UserAuthRespVo userAuthRespVo = userAuthService.userAuth(authPageVo.getUserType(), json);
            if (userAuthRespVo.getCode() == 2) {
                senderVo.setStatus("500");
                senderVo.setMessage("未找到该用户");
            }
            if (userAuthRespVo.getCode() == 3) {
                senderVo.setStatus("500");
                senderVo.setMessage("密码错误");
            }
            if (userAuthRespVo.getCode() == 4) {
                senderVo.setStatus("500");
                senderVo.setMessage("未知错误");
            }
            //鉴权成功
            if (userAuthRespVo.getCode() == 1) {
                //生成code
                String codeString = UUID.randomUUID().toString();
                /**将userid存入visitid的hash**/
                stringRedisTemplate.boundHashOps(visitId + visitID).put("userId",userAuthRespVo.getUserId());
                /**将code与visitid关联**/
                stringRedisTemplate.opsForValue().set(code + codeString, visitID, 5 * 60, TimeUnit.SECONDS);
                LoginResp loginResp = LoginResp.builder().clientID(authPageVo.getClient_id()).Code(codeString).redirectURI(authPageVo.getRedirect_uri()).responseType(authPageVo.getResponse_type()).state(authPageVo.getState()).build();
                senderVo.setStatus("200");
                senderVo.setMessage("验证成功正在跳转页面");
                senderVo.setAttachment(loginResp);
            }
        } catch (Exception e) {
            log.error("转换错误", e);
            senderVo.setStatus("400");
            senderVo.setMessage("服务内部错误");
            e.printStackTrace();
        }
        return senderVo;
    }
}
