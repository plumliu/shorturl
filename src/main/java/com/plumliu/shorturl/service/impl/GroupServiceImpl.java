package com.plumliu.shorturl.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

        UserLoginRespDTO user = Optional.ofNullable(UserContext.getUser())
                .orElseThrow(() -> new ClientException(BaseErrorCode.INVALID_TOKEN));

        String gid = saveGroupCore(user.getUsername(), groupCreateReqDTO.getName());

        return new GroupCreateRespDTO(gid, groupCreateReqDTO.getName());
    }

    @Override
    public GroupCreateRespDTO createGroupWithoutUser(String username, String groupName) {
        return new GroupCreateRespDTO(saveGroupCore(username, groupName), username);
    }

    private String saveGroupCore(String username, String groupName) {
        String gid = null;
        do {
            gid = IdUtil.fastSimpleUUID();
        } while (hasGid(username, gid));

        GroupDO groupDO = new GroupDO();
        groupDO.setGid(gid);
        groupDO.setSortOrder(0);
        groupDO.setUsername(username);
        groupDO.setName(groupName);

        baseMapper.insert(groupDO);
        return gid;
    }

    private boolean hasGid(String username, String gid) {
        return baseMapper.exists(new LambdaQueryWrapper<GroupDO>()
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, username));
    }

}
