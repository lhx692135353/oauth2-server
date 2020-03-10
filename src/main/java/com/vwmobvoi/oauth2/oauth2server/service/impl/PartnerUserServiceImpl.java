package com.vwmobvoi.oauth2.oauth2server.service.impl;

import com.vwmobvoi.oauth2.oauth2server.entity.po.PartnerUser;
import com.vwmobvoi.oauth2.oauth2server.repo.PartnerUserRepository;
import com.vwmobvoi.oauth2.oauth2server.service.PartnerUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program oauth2-server
 * @description: 合作方用户service实现
 * @author: liuhx
 * @create: 2020/02/24 15:20
 */
@Service
public class PartnerUserServiceImpl implements PartnerUserService {
    @Resource
    PartnerUserRepository partnerUserRepository;

    @Override
    public PartnerUser insert(PartnerUser partnerUser) {
        return partnerUserRepository.insert(partnerUser);
    }

    @Override
    public void delete(String id) {
        partnerUserRepository.deleteById(id);
    }

    @Override
    public PartnerUser findByClientId(String clientId) {
        return partnerUserRepository.findByClientId(clientId);
    }
}
