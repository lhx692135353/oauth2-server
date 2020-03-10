package com.vwmobvoi.oauth2.oauth2server.repo;

import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @program oauth2-server
 * @description:
 * @author: liuhx
 * @create: 2020/02/21 17:56
 */
public interface PartnerUserRepository extends MongoRepository<PartnerUser, String> {
    PartnerUser findByClientId(String clientId);
}
