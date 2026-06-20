package com.furbaby.furbaby.service;

import com.furbaby.furbaby.vo.NotifyItemVO;
import com.furbaby.furbaby.vo.PageResult;

import java.util.Map;

public interface INotifyService {

    PageResult<NotifyItemVO> listNotifications(String token, Integer page, Integer size);

    Map<String, Boolean> markAsRead(String token, Long id);
}
