package com.plumliu.shorturl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plumliu.shorturl.domain.entity.GroupDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper extends BaseMapper<GroupDO> {
}
