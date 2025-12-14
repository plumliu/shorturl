package com.plumliu.shorturl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;
import com.plumliu.shorturl.mapper.ShortLinkMapper;
import com.plumliu.shorturl.service.ShortLinkService;
import com.plumliu.shorturl.utils.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public String createShortLink(String originalUrl) {
        String shortUri = HashUtil.hashToBase62(originalUrl);

        String domain = "http://localhost:8080/s/";
        String fullShortUrl = domain + shortUri;

        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setOriginalUrl(originalUrl);
        shortLinkDO.setDomain(domain);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setFullShortUrl(fullShortUrl);

        try {
            baseMapper.insert(shortLinkDO);
            return shortUri;
        } catch (DuplicateKeyException e){
            log.warn("Hash冲突触发: 长链={}，短码={}", originalUrl, shortUri);
            return createShortLink(originalUrl + UUID.randomUUID().toString());
        }

    }
}
