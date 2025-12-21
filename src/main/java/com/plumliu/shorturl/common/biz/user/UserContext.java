package com.plumliu.shorturl.common.biz.user;


import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;

public class UserContext {

    private static final ThreadLocal<UserLoginRespDTO> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(UserLoginRespDTO loginRespDTO) {
        USER_THREAD_LOCAL.set(loginRespDTO);
    }

    public static UserLoginRespDTO getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
