package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @program oauth2-server
 * @description: 用户认证返回信息类
 * @author: liuhx
 * @create: 2020/02/25 16:26
 */
@Data
@Builder
public class UserAuthRespVo {
    /**
     * 1、成功
     * 2、未找到用户
     * 3、密码不正确
     * 4、未知错误
     */
    private int code;
    private String userId;
}
