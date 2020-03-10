package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: token获取与重置请求参数
 * @author: liuhx
 * @create: 2020/02/25 18:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenReqVo {
    @JsonProperty("grant_type")
    private String grant_type;
    @JsonProperty("code")
    private String code;
    @JsonProperty("redirect_uri")
    private String redirect_uri;
    @JsonProperty("client_id")
    private String client_id;
    @JsonProperty("client_secret")
    private String client_secret;
    @JsonProperty("refresh_token")
    private String refresh_token;
}
