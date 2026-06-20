package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.INotifyService;
import com.furbaby.furbaby.vo.NotifyItemVO;
import com.furbaby.furbaby.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "通知管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotifyController {

    private final INotifyService notifyService;

    @Operation(summary = "消息列表", description = "分页查询当前用户的通知消息列表")
    @GetMapping("/list")
    public Result<PageResult<NotifyItemVO>> listNotifications(@RequestHeader("Authorization") String authHeader,
                                                                @RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        String token = authHeader.replace("Bearer ", "");
        PageResult<NotifyItemVO> result = notifyService.listNotifications(token, page, size);
        return Result.success(result);
    }
}
