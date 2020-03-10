package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: 认证页面接收参数
 * @author: liuhx
 * @create: 2020/02/21 12:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPageVo {
    @JsonProperty("redirect_uri")
    private String redirect_uri;
    @JsonProperty("client_id")
    private String client_id;
    @JsonProperty("response_type")
    private String response_type;
    @JsonProperty("state")
    private String state;
    /**
     * 下半部为redis存储冗余值
     */
    private String visitId;
    private String userType;
    private String userSecret;
    private String userId;
}
