package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.furbaby.furbaby.vo.OrderCreateVO;
import com.furbaby.furbaby.vo.OrderItemVO;
import com.furbaby.furbaby.vo.PageResult;

public interface IOrderService extends IService<Order> {

    OrderCreateVO createOrder(String token, OrderCreateDTO createDTO);

    PageResult<OrderItemVO> listOrders(String token, String status, Integer page, Integer size);
}
