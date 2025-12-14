package com.plumliu.shorturl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {
}
