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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Operation(summary = "标记已读", description = "将指定通知标记为已读状态")
    @PutMapping("/read/{id}")
    public Result<Map<String, Boolean>> markAsRead(@RequestHeader("Authorization") String authHeader,
                                                    @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, Boolean> result = notifyService.markAsRead(token, id);
        return Result.success(result);
    }

    @Operation(summary = "未读数量", description = "获取当前用户的未读通知数量")
    @GetMapping("/unread-count")
    public Result<Map<String, Integer>> getUnreadCount(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, Integer> result = notifyService.getUnreadCount(token);
        return Result.success(result);
    }
}
