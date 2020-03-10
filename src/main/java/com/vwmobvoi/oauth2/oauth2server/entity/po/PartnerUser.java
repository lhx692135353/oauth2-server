package com.vwmobvoi.oauth2.oauth2server.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @program oauth2-server
 * @description: 合作商用户
 * @author: liuhx
 * @create: 2020/02/21 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "partner_user")
public class PartnerUser {
    @Id
    private String id;
    @Indexed(unique = true)
    private String clientId;
    private String clientSecret;
    private String name;
    private String userType;
    private String authPageName;
    @CreatedDate
    private LocalDateTime createDate;
}
