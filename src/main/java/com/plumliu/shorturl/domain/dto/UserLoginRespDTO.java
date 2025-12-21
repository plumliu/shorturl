package com.plumliu.shorturl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRespDTO {

    private Long id;

    private String username;

    private String realName;

    private String mail;

    private String phone;

    private String token;
}
