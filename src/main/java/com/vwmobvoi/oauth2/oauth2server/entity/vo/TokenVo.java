package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: token存储类
 * @author: liuhx
 * @create: 2020/02/26 11:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVo {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String clientId;
    private String userType;
}
