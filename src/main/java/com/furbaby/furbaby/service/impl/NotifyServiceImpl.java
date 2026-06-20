package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.furbaby.furbaby.entity.Notification;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.NotificationMapper;
import com.furbaby.furbaby.service.INotifyService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.NotifyItemVO;
import com.furbaby.furbaby.vo.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements INotifyService {

    private final NotificationMapper notificationMapper;
    private final JWTUtils jwtUtils;

    @Override
    public PageResult<NotifyItemVO> listNotifications(String token, Integer page, Integer size) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        LambdaQueryWrapper<Notification> wrapper = Wrappers.<Notification>lambdaQuery()
                .eq(Notification::getUserId, userId)
                .orderByDesc(Notification::getCreateTime);

        long total = notificationMapper.selectCount(wrapper);
        long pages = (total + size - 1) / size;
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + "," + size);

        List<Notification> notifications = notificationMapper.selectList(wrapper);

        List<NotifyItemVO> records = notifications.stream().map(n -> NotifyItemVO.builder()
                .id(n.getId())
                .type(n.getType())
                .title(n.getTitle())
                .content(n.getContent())
                .read(n.getIsRead())
                .createTime(n.getCreateTime())
                .build()).collect(Collectors.toList());

        return PageResult.<NotifyItemVO>builder()
                .total(total)
                .pages(pages)
                .records(records)
                .build();
    }

    @Override
    public Map<String, Boolean> markAsRead(String token, Long id) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Notification notification = notificationMapper.selectOne(
                Wrappers.<Notification>lambdaQuery()
                        .eq(Notification::getId, id)
                        .eq(Notification::getUserId, userId));
        if (notification == null) {
            throw new NoRegisterException("通知不存在");
        }

        notification.setIsRead(true);
        notificationMapper.updateById(notification);

        return Map.of("success", true);
    }

    @Override
    public Map<String, Integer> getUnreadCount(String token) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        long count = notificationMapper.selectCount(
                Wrappers.<Notification>lambdaQuery()
                        .eq(Notification::getUserId, userId)
                        .eq(Notification::getIsRead, false));

        return Map.of("count", (int) count);
    }
}
