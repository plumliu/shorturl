package com.plumliu.shorturl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plumliu.shorturl.domain.dto.GroupCreateReqDTO;
import com.plumliu.shorturl.domain.dto.GroupCreateRespDTO;
import com.plumliu.shorturl.domain.entity.GroupDO;

public interface GroupService extends IService<GroupDO> {

    /***
     * @param groupCreateReqDTO 创建组别请求dto
     * @return 创建的分组信息
     */
    GroupCreateRespDTO createGroup(GroupCreateReqDTO groupCreateReqDTO);

    /**
     * 新增短链接分组
     * @param username 用户名
     * @param groupName 分组名
     * @return 创建的分组信息
     */
    GroupCreateRespDTO createGroupWithoutUser(String username, String groupName);
}
