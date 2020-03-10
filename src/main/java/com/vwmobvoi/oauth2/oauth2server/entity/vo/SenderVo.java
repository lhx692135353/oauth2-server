package com.vwmobvoi.oauth2.oauth2server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program oauth2-server
 * @description: 一般接口返回实体类
 * @author: liuhx
 * @create: 2020/02/24 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SenderVo<T> {
    private String status;
    private String message;
    private T attachment;
}
