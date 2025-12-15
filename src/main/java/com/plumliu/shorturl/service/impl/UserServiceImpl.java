package com.plumliu.shorturl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.domain.entity.UserDO;
import com.plumliu.shorturl.mapper.UserMapper;
import com.plumliu.shorturl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        UserDO userDO = BeanUtil.toBean(userRegisterReqDTO, UserDO.class);
        userDO.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));

        try {
            baseMapper.insert(userDO);
        } catch (DuplicateKeyException ex) {
            throw new RuntimeException("用户名或手机号或邮箱已存在");
        }

    }

}
