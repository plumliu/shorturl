package com.plumliu.shorturl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRespDTO {
    String gid;

    String name;
}
