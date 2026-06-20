package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.OrderCancelDTO;
import com.furbaby.furbaby.dto.OrderCreateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IOrderService;
import com.furbaby.furbaby.vo.OrderCreateVO;
import com.furbaby.furbaby.vo.OrderDetailVO;
import com.furbaby.furbaby.vo.OrderItemVO;
import com.furbaby.furbaby.vo.OrderStatusVO;
import com.furbaby.furbaby.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Operation(summary = "我的订单列表", description = "分页查询当前用户的订单列表，可按状态筛选")
    @GetMapping("/list")
    public Result<PageResult<OrderItemVO>> listOrders(@RequestHeader("Authorization") String authHeader,
                                                       @RequestParam(required = false) String status,
                                                       @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        String token = authHeader.replace("Bearer ", "");
        PageResult<OrderItemVO> result = orderService.listOrders(token, status, page, size);
        return Result.success(result);
    }

    @Operation(summary = "订单详情", description = "根据订单ID获取订单完整详情")
    @GetMapping("/detail/{id}")
    public Result<OrderDetailVO> getOrderDetail(@PathVariable Long id) {
        OrderDetailVO detail = orderService.getOrderDetail(id);
        return Result.success(detail);
    }

    @Operation(summary = "取消预约", description = "取消指定订单，仅待支付和已支付状态可取消，同时恢复档期库存")
    @PutMapping("/cancel/{id}")
    public Result<Map<String, String>> cancelOrder(@RequestHeader("Authorization") String authHeader,
                                                    @PathVariable Long id,
                                                    @RequestBody(required = false) OrderCancelDTO cancelDTO) {
        String token = authHeader.replace("Bearer ", "");
        if (cancelDTO == null) {
            cancelDTO = new OrderCancelDTO();
        }
        Map<String, String> result = orderService.cancelOrder(token, id, cancelDTO);
        return Result.success(result);
    }

    @Operation(summary = "查询订单状态", description = "查询订单当前状态及完整状态流转时间线")
    @GetMapping("/status/{id}")
    public Result<OrderStatusVO> getOrderStatus(@PathVariable Long id) {
        OrderStatusVO statusVO = orderService.getOrderStatus(id);
        return Result.success(statusVO);
    }
}
