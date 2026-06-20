package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.furbaby.furbaby.vo.OrderCreateVO;

public interface IOrderService extends IService<Order> {

    OrderCreateVO createOrder(String token, OrderCreateDTO createDTO);
}
