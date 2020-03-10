package com.vwmobvoi.oauth2.oauth2server.controller;

import com.vwmobvoi.oauth2.oauth2server.entity.vo.AuthPageVo;
import com.vwmobvoi.oauth2.oauth2server.entity.vo.VisitVo;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @program oauth2-server
 * @description:
 * @author: liuhx
 * @create: 2020/02/21 11:41
 */
@Controller
@RequestMapping("auth")
public class PartnerAuthPageController {

    @Resource
    private PartnerAuthService partnerAuthService;

    @GetMapping("authorize")
    public ModelAndView authorize(AuthPageVo authPageVo) {
        ModelAndView modelAndView = new ModelAndView();
        VisitVo visitVo = partnerAuthService.applyAuth(authPageVo);
        modelAndView.setStatus(HttpStatus.OK);
        if (visitVo.getMessage() == null) {
            modelAndView.setViewName(visitVo.getAuthPageName());
            modelAndView.addObject("visit_id", visitVo.getVisitId());
        } else {
            modelAndView.setViewName("error");
            modelAndView.addObject("message", visitVo.getMessage());
        }
        return modelAndView;
    }
}
