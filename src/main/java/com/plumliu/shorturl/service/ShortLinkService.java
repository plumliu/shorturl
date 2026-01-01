package com.plumliu.shorturl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plumliu.shorturl.domain.dto.ShortLinkCreateReqDTO;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;

public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     *
     * @param shortLinkCreateReqDTO 创建短链接的请求
     * @return 完整的短链接
     */
    String createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);
}
