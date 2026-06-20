package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IOrderService;
import com.furbaby.furbaby.vo.OrderCreateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "预约管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;

    @Operation(summary = "创建预约", description = "选择商家、宠物和日期创建寄养预约订单")
    @PostMapping("/create")
    public Result<OrderCreateVO> createOrder(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody OrderCreateDTO createDTO) {
        String token = authHeader.replace("Bearer ", "");
        OrderCreateVO result = orderService.createOrder(token, createDTO);
        return Result.success(result);
    }
}
