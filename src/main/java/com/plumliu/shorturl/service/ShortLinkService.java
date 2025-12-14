package com.plumliu.shorturl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;

public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     *
     * @param originalUrl 原始长链接
     * @return 完整的短链接
     */
    String createShortLink(String originalUrl);
}
