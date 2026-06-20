package com.furbaby.furbaby.service;

import com.furbaby.furbaby.vo.NotifyItemVO;
import com.furbaby.furbaby.vo.PageResult;

public interface INotifyService {

    PageResult<NotifyItemVO> listNotifications(String token, Integer page, Integer size);
}
