package com.vwmobvoi.oauth2.oauth2server.controller;

import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.SenderVo;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @program oauth2-server
 * @description: 合作商管理
 * @author: liuhx
 * @create: 2020/02/24 15:10
 */
@RestController
@RequestMapping("partner-user")
@Slf4j
public class PartnerManageController {

    @Resource
    PartnerUserService partnerUserService;

    @PostMapping("insert")
    public SenderVo<PartnerUser> insertPartnerUser(@Valid PartnerInsert partnerInsert, BindingResult results) {
        SenderVo<PartnerUser> senderVo = new SenderVo<>();
        if (results.hasErrors()) {
            senderVo.setMessage(results.getFieldError().getField() + results.getFieldError().getDefaultMessage());
            senderVo.setStatus("400");
            return senderVo;
        }
        senderVo.setMessage("添加成功");
        senderVo.setStatus("200");
        PartnerUser partnerUser = PartnerUser.builder().userType(partnerInsert.getUserType()).
                authPageName(partnerInsert.getAuthPageName()).name(partnerInsert.getName()).
                clientId(partnerInsert.getClientId()).clientSecret(UUID.randomUUID().toString().replaceAll("-", "")).createDate(LocalDateTime.now()).build();
        senderVo.setAttachment(partnerUserService.insert(partnerUser));
        return senderVo;
    }

    @Data
    public class PartnerInsert {
        @NotBlank
        private String name;
        @NotBlank
        private String userType;
        @NotBlank
        private String authPageName;
        @NotBlank
        private String clientId;
    }


    @PostMapping("delete")
    public SenderVo<String> deletePartnerUser(String id) {
        SenderVo<String> senderVo = new SenderVo<>();
        try {
            senderVo.setMessage("删除成功");
            senderVo.setStatus("200");
            partnerUserService.delete(id);
        } catch (Exception e) {
            log.error("删除问题{}", e);
            senderVo.setMessage("删除失败");
            senderVo.setStatus("400");
            e.printStackTrace();
        }

        return senderVo;
    }
}
