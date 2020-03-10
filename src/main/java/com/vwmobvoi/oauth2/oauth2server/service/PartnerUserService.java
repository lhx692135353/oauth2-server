package com.vwmobvoi.oauth2.oauth2server.service;

import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;

public interface PartnerUserService {
    PartnerUser insert(PartnerUser partnerUser);


    void delete(String id);

    PartnerUser findByClientId(String clientId);
}
