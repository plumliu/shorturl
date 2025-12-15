package com.plumliu.shorturl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plumliu.shorturl.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
