package com.plumliu.shorturl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.common.convention.errorcode.UserErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.dto.UserLoginReqDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.domain.entity.UserDO;
import com.plumliu.shorturl.mapper.UserMapper;
import com.plumliu.shorturl.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, requestParam.getUsername())
                .or().eq(UserDO::getMail, requestParam.getUsername())
                .or().eq(UserDO::getPhone, requestParam.getUsername());

        UserDO userDO = Optional.ofNullable(baseMapper.selectOne(queryWrapper))
                .orElseThrow(() -> new ClientException(UserErrorCode.USER_NULL));

        if (!passwordEncoder.matches(requestParam.getPassword(), userDO.getPassword())) {
            throw new ClientException(UserErrorCode.PASSWORD_ERROR);
        }

        String token = UUID.randomUUID().toString();

        UserLoginRespDTO respDTO = BeanUtil.toBean(userDO, UserLoginRespDTO.class);
        respDTO.setToken(token);

        String key = "login:token:" + token;
        redisTemplate.opsForValue().set(key, respDTO, 30, TimeUnit.MINUTES);

        return respDTO;
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        UserDO userDO = BeanUtil.toBean(userRegisterReqDTO, UserDO.class);
        userDO.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));

        try {
            baseMapper.insert(userDO);
        } catch (DuplicateKeyException ex) {
            throw new ClientException(ex.getMessage(), UserErrorCode.USER_EXIST);
        }
    }

    @Override
    public boolean hasUsername(String username) {
        return baseMapper.exists(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getUsername, username));
    }

    @Override
    public boolean hasMail(String mail) {
        return baseMapper.exists(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getMail, mail));
    }

    @Override
    public boolean hasPhone(String phone) {
        return baseMapper.exists(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getPhone, phone));
    }

}
