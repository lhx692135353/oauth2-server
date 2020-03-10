package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: 申请权限访问返回
 * @author: liuhx
 * @create: 2020/02/21 16:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitVo {
    private String message;
    private String visitId;
    private String authPageName;
}
