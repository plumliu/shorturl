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


    /**
     *
     * @param username 输入的用户名
     * @return 用户名是否存在
     */
    boolean hasUsername(String username);

    /**
     *
     * @param mail 输入的邮箱
     * @return 邮箱是否存在
     */
    boolean hasMail(String mail);

    /**
     *
     * @param phone 输入的手机号
     * @return 手机号是否存在
     */
    boolean hasPhone(String phone);


}
