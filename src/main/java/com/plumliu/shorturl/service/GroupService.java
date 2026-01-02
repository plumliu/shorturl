package com.plumliu.shorturl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plumliu.shorturl.domain.dto.GroupCreateReqDTO;
import com.plumliu.shorturl.domain.dto.GroupCreateRespDTO;
import com.plumliu.shorturl.domain.entity.GroupDO;

public interface GroupService extends IService<GroupDO> {

    /***
     * @param groupCreateReqDTO 创建组别请求dto
     */
    GroupCreateRespDTO createGroup(GroupCreateReqDTO groupCreateReqDTO);

}
