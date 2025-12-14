package com.plumliu.shorturl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.plumliu.shorturl.domain.entity.ShortLinkDO;
import com.plumliu.shorturl.service.ShortLinkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @Operation(summary = "创建短链接")
    @PostMapping("/api/short-link/save")
    public String createShortLink(@RequestParam("originalUrl") String originalUrl) {
        String shortUrl = shortLinkService.createShortLink(originalUrl);
        return shortLinkService.createShortLink(originalUrl);
    }

    @Operation(summary = "短链接跳转")
    @GetMapping("/s/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, HttpServletResponse response) throws IOException {

        LambdaQueryWrapper<ShortLinkDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShortLinkDO::getShortUri, shortUri);
        wrapper.eq(ShortLinkDO::getEnableStatus, 1);

        ShortLinkDO shortLinkDO = shortLinkService.getOne(wrapper);

        if (shortLinkDO == null) {
            log.warn("未找到 {}", shortUri);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short Link not found");
            return;
        }

        response.sendRedirect(shortLinkDO.getOriginalUrl());
    }
}
