package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: token认证返回类
 * @author: liuhx
 * @create: 2020/02/25 17:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRespVo {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("error")
    private String error;
}
