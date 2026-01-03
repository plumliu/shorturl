package com.plumliu.shorturl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plumliu.shorturl.common.convention.errorcode.UserErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.dto.UserLoginReqDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.domain.entity.UserDO;
import com.plumliu.shorturl.mapper.GroupMapper;
import com.plumliu.shorturl.mapper.UserMapper;
import com.plumliu.shorturl.service.GroupService;
import com.plumliu.shorturl.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final GroupService groupService;

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> LUA_LOGIN_SCRIPT;

    static {
        LUA_LOGIN_SCRIPT = new DefaultRedisScript<>();
        LUA_LOGIN_SCRIPT.setScriptText("""
            local returnValue = 0
            local oldToken = redis.call('GET', KEYS[1])
            if oldToken and oldToken ~= '' then
                redis.call('DEL', 'login:token:' .. oldToken)
                returnValue = 1
            end
            redis.call('SET', KEYS[2], ARGV[2])
            redis.call('EXPIRE', KEYS[2], ARGV[3] * 60)
            redis.call('SET', KEYS[1], ARGV[1])
            redis.call('EXPIRE', KEYS[1], ARGV[3] * 60)
            return returnValue
            """);
        LUA_LOGIN_SCRIPT.setResultType(Long.class); // 设置返回值类型
    }

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

        String userId = String.valueOf(userDO.getId());
        String token = UUID.randomUUID().toString();

        String userLoginKey = "login:user:" + userId;
        String tokenKey = "login:token:" + token;

        UserLoginRespDTO respDTO = BeanUtil.toBean(userDO, UserLoginRespDTO.class);
        respDTO.setToken(token);
        String userInfoJson = JSONUtil.toJsonStr(respDTO);

        Long result = stringRedisTemplate.execute(
                LUA_LOGIN_SCRIPT,
                Arrays.asList(userLoginKey, tokenKey),
                token,
                userInfoJson,
                "30"
        );

        if (result != null && result == 1) {
            log.warn("用户异地登录预警: 用户ID={}，旧Token已被强制踢下线", userId);
        }

        return respDTO;
    }

    @Override
    @Transactional(rollbackFor = {ClientException.class, RuntimeException.class})
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        UserDO userDO = BeanUtil.toBean(userRegisterReqDTO, UserDO.class);
        userDO.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));

        try {
            baseMapper.insert(userDO);
            groupService.createGroupWithoutUser(userRegisterReqDTO.getUsername(), "默认分组");

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
