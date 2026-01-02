package com.plumliu.shorturl.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortLinkCreateReqDTO {

    @NotBlank(message = "原始链接不能为空")
    private String originalUrl;

    private String description;

    @NotNull(message = "有效期类型不能为空")
    private Integer validDateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime validDate;

    private String gid;
}