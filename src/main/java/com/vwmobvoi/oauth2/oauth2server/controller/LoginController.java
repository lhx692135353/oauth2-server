package com.vwmobvoi.oauth2.oauth2server.controller;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.LoginResp;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.SenderVo;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerAuthService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @program oauth2-server
 * @description: 登录
 * @author: liuhx
 * @create: 2020/02/21 18:40
 */

@RequestMapping("auth")
@Slf4j
@RestController
public class LoginController {

    @Resource
    PartnerAuthService partnerAuthService;

    @PostMapping("login")
    public SenderVo<LoginResp> login(@Valid LoginReq loginReq, BindingResult results) {
        SenderVo<LoginResp> senderVo = new SenderVo<>();
        if (results.hasErrors()) {
            senderVo.setMessage(results.getFieldError().getField() + results.getFieldError().getDefaultMessage());
            senderVo.setStatus("400");
            return senderVo;
        }
        try {
            senderVo = partnerAuthService.userLogin(loginReq.visitId, loginReq.json);
        } catch (Exception e) {
            log.error("用户登录错误{}", e);
            e.printStackTrace();
        }
        return senderVo;
    }

    @Data
    public class LoginReq {
        @NotBlank
        private String visitId;
        @NotBlank
        private String json;
    }
}
