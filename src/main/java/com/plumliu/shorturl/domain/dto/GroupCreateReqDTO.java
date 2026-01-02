package com.plumliu.shorturl.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupCreateReqDTO {

    @NotBlank(message = "分组名不能为空")
    private String name;

    private Integer sortOrder;
}
