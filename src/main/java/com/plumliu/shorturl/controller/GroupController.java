package com.plumliu.shorturl.controller;

import com.plumliu.shorturl.domain.dto.GroupCreateReqDTO;
import com.plumliu.shorturl.domain.dto.GroupCreateRespDTO;
import com.plumliu.shorturl.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "短链接分组接口")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/api/group/save")
    @Operation(summary = "新增短链接分组")
    public GroupCreateRespDTO createGroup(@Valid @RequestBody GroupCreateReqDTO groupCreateReqDTO) {
        return groupService.createGroup(groupCreateReqDTO);
    }
}
