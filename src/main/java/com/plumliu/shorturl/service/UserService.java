package com.plumliu.shorturl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.domain.entity.UserDO;

public interface UserService extends IService<UserDO> {

    /***
     *
     * @param userRegisterReqDTO 注册DTO
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

}
