package com.plumliu.shorturl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.common.convention.errorcode.ShortLinkErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;
import com.plumliu.shorturl.mapper.ShortLinkMapper;
import com.plumliu.shorturl.service.ShortLinkService;
import com.plumliu.shorturl.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public String createShortLink(String originalUrl) {
        // 入口方法，初始重试次数为 0
        return createShortLink(originalUrl, 0);
    }

    private String createShortLink(String originalUrl, int retryCount) {
        String hashInput = originalUrl + (retryCount > 0 ? UUID.randomUUID().toString() : "");

        String shortUri = HashUtil.hashToBase62(hashInput);

        String domain = "http://localhost:8080/s/"; // TODO: 后续放到配置文件中
        String fullShortUrl = domain + shortUri;

        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setOriginalUrl(originalUrl);
        shortLinkDO.setDomain(domain);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setFullShortUrl(fullShortUrl);
        shortLinkDO.setEnableStatus(1);

        try {
            baseMapper.insert(shortLinkDO);
            return shortUri;
        } catch (DuplicateKeyException e) {
            log.warn("Hash冲突触发: 短码={}", shortUri);
            if (retryCount > 10) {
                throw new ClientException(ShortLinkErrorCode.TOO_MANY_ATTEMPTS);
            }
            return createShortLink(originalUrl, retryCount + 1);
        }
    }
}
