package com.plumliu.shorturl.controller;

import com.plumliu.shorturl.domain.dto.UserRegisterReqDTO;
import com.plumliu.shorturl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "用户管理接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/api/user/register")
    public String register(@Valid @RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return "注册成功";
    }

}
