package com.plumliu.shorturl.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.common.biz.user.UserContext;
import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.dto.GroupCreateReqDTO;
import com.plumliu.shorturl.domain.dto.GroupCreateRespDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import com.plumliu.shorturl.domain.entity.GroupDO;
import com.plumliu.shorturl.mapper.GroupMapper;
import com.plumliu.shorturl.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    @Override
    public GroupCreateRespDTO createGroup(GroupCreateReqDTO groupCreateReqDTO) {
        String gid = IdUtil.fastSimpleUUID();

        UserLoginRespDTO user = Optional.ofNullable(UserContext.getUser())
                .orElseThrow(() -> new ClientException(BaseErrorCode.INVALID_TOKEN));

        GroupDO groupDO = new GroupDO();
        groupDO.setGid(gid);
        groupDO.setUsername(user.getUsername());
        groupDO.setSortOrder(groupCreateReqDTO.getSortOrder());
        groupDO.setName(groupCreateReqDTO.getName());

        baseMapper.insert(groupDO);

        return new GroupCreateRespDTO(gid, groupCreateReqDTO.getName());
    }
}
