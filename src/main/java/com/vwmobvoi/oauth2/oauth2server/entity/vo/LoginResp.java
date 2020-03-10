package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: 登录返回值
 * @author: liuhx
 * @create: 2020/02/21 18:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResp {
    @JsonProperty("redirect_uri")
    private String redirectURI;
    @JsonProperty("client_id")
    private String clientID;
    @JsonProperty("response_type")
    private String responseType;
    @JsonProperty("state")
    private String state;
    @JsonProperty("code")
    private String Code;
}
