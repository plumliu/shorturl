package com.plumliu.shorturl.controller;

import com.plumliu.shorturl.common.convention.result.Result;
import com.plumliu.shorturl.domain.dto.UserLoginReqDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "用户管理接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/api/user/login")
    public UserLoginRespDTO login(@Valid @RequestBody UserLoginReqDTO userLoginReqDTO) {
        return userService.login(userLoginReqDTO);
    }


    @Operation(summary = "用户注册")
    @PostMapping("/api/user/register")
    public void register(@Valid @RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
    }

    @Operation(summary = "检查用户名是否存在")
    @GetMapping("/api/user/has-username")
    public Boolean hasUsername(@NotBlank @RequestParam("username") String username) {
        return userService.hasUsername(username);
    }

    @Operation(summary = "检查邮箱是否存在")
    @GetMapping("/api/user/has-mail")
    public Boolean hasMail(@NotBlank @RequestParam("mail") String mail) {
        return userService.hasMail(mail);
    }

    @Operation(summary = "检查手机号是否存在")
    @GetMapping("/api/user/has-phone")
    public Boolean hasPhone(@NotBlank @RequestParam("phone") String phone) {
        return userService.hasPhone(phone);
    }
}
