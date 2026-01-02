package com.plumliu.shorturl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.common.biz.user.UserContext;
import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.errorcode.ShortLinkErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.dto.ShortLinkCreateReqDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;
import com.plumliu.shorturl.mapper.ShortLinkMapper;
import com.plumliu.shorturl.service.ShortLinkService;
import com.plumliu.shorturl.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public String createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        // 入口方法，初始重试次数为 0
        return createShortLink(shortLinkCreateReqDTO, 0);
    }

    private String createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO, int retryCount) {
        String originalUrl = shortLinkCreateReqDTO.getOriginalUrl();

        String hashInput = originalUrl + (retryCount > 0 ? UUID.randomUUID().toString() : "");

        String shortUri = HashUtil.hashToBase62(hashInput);

        String domain = "http://localhost:8080/s/"; // TODO: 后续放到配置文件中
        String fullShortUrl = domain + shortUri;

        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setOriginalUrl(originalUrl);
        shortLinkDO.setDomain(domain);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setFullShortUrl(fullShortUrl);

        UserLoginRespDTO user = Optional.ofNullable(UserContext.getUser())
                .orElseThrow(() -> new ClientException(BaseErrorCode.INVALID_TOKEN));

        shortLinkDO.setUserId(user.getId());
        shortLinkDO.setCreateType(1);
        shortLinkDO.setGid(shortLinkCreateReqDTO.getGid() != null ? shortLinkCreateReqDTO.getGid() : "default");
        shortLinkDO.setEnableStatus(1);

        shortLinkDO.setDescription(shortLinkCreateReqDTO.getDescription());
        shortLinkDO.setValidDateType(shortLinkCreateReqDTO.getValidDateType());
        shortLinkDO.setValidDate(shortLinkCreateReqDTO.getValidDate());
        shortLinkDO.setTotalPv(0);
        shortLinkDO.setTotalUv(0);
        shortLinkDO.setTotalUip(0);

        try {
            baseMapper.insert(shortLinkDO);
            return shortUri;
        } catch (DuplicateKeyException e) {
            log.warn("Hash冲突触发: 短码={}", shortUri);
            if (retryCount > 10) {
                throw new ClientException(ShortLinkErrorCode.TOO_MANY_ATTEMPTS);
            }
            return createShortLink(shortLinkCreateReqDTO, retryCount + 1);
        }
    }
}
