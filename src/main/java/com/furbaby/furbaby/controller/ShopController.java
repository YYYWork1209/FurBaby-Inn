package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.ScheduleDTO;
import com.furbaby.furbaby.dto.ScheduleUpdateDTO;
import com.furbaby.furbaby.dto.ShopRegisterDTO;
import com.furbaby.furbaby.dto.ShopUpdateDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IShopService;
import com.furbaby.furbaby.vo.DashboardStatsVO;
import com.furbaby.furbaby.vo.MerchantShopVO;
import com.furbaby.furbaby.vo.OrderItemVO;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.SchedulePublishVO;
import com.furbaby.furbaby.vo.ScheduleUpdateResultVO;
import com.furbaby.furbaby.vo.ShopDetailVO;
import com.furbaby.furbaby.vo.ShopRegisterVO;
import com.furbaby.furbaby.vo.ShopScheduleVO;
import com.furbaby.furbaby.vo.ShopVO;

import java.util.Map;
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

@Slf4j
@Tag(name = "商家管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private final IShopService shopService;

    @Operation(summary = "商家列表", description = "分页搜索已审核通过的商家")
    @GetMapping("/list")
    public Result<PageResult<ShopVO>> listShops(@RequestParam(required = false) String keyword,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 @RequestParam(required = false) String sort) {
        PageResult<ShopVO> result = shopService.listShops(keyword, page, size, sort);
        return Result.success(result);
    }

    @Operation(summary = "商家详情", description = "根据商家ID获取商家详细信息")
    @GetMapping("/detail/{id}")
    public Result<ShopDetailVO> getShopDetail(@PathVariable Long id) {
        ShopDetailVO detail = shopService.getShopDetail(id);
        return Result.success(detail);
    }

    @Operation(summary = "商家档期", description = "查询指定商家在日期范围内的档期安排")
    @GetMapping("/schedule/{shopId}")
    public Result<ShopScheduleVO> getShopSchedule(@PathVariable Long shopId,
                                                    @RequestParam(required = false) String startDate,
                                                    @RequestParam(required = false) String endDate) {
        ShopScheduleVO schedule = shopService.getShopSchedule(shopId, startDate, endDate);
        return Result.success(schedule);
    }

    @Operation(summary = "发布档期", description = "商家为自己的店铺批量设置档期")
    @PostMapping("/schedule")
    public Result<SchedulePublishVO> publishSchedule(@RequestHeader("Authorization") String authHeader,
                                                       @RequestBody ScheduleDTO scheduleDTO) {
        String token = authHeader.replace("Bearer ", "");
        SchedulePublishVO result = shopService.publishSchedule(token, scheduleDTO);
        return Result.success(result);
    }

    @Operation(summary = "扣减档期库存", description = "扣减或恢复指定日期的档期名额")
    @PutMapping("/schedule/{date}")
    public Result<ScheduleUpdateResultVO> updateSchedule(@RequestHeader("Authorization") String authHeader,
                                                           @PathVariable String date,
                                                           @RequestBody ScheduleUpdateDTO updateDTO) {
        String token = authHeader.replace("Bearer ", "");
        ScheduleUpdateResultVO result = shopService.updateSchedule(token, date, updateDTO);
        return Result.success(result);
    }

    @Operation(summary = "商家入驻", description = "已注册用户申请成为商家")
    @PostMapping("/register")
    public Result<ShopRegisterVO> registerShop(@RequestHeader("Authorization") String authHeader,
                                                 @RequestBody ShopRegisterDTO registerDTO) {
        String token = authHeader.replace("Bearer ", "");
        ShopRegisterVO result = shopService.registerShop(token, registerDTO);
        return Result.success(result);
    }

    @Operation(summary = "获取当前商家店铺信息", description = "已登录商家查看自己的店铺信息")
    @GetMapping("/my")
    public Result<MerchantShopVO> getMyShop(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        MerchantShopVO result = shopService.getMyShop(token);
        return Result.success(result);
    }

    @Operation(summary = "更新店铺信息", description = "已登录商家更新自己的店铺信息")
    @PutMapping("/my")
    public Result<MerchantShopVO> updateMyShop(@RequestHeader("Authorization") String authHeader,
                                                 @RequestBody ShopUpdateDTO updateDTO) {
        String token = authHeader.replace("Bearer ", "");
        MerchantShopVO result = shopService.updateMyShop(token, updateDTO);
        return Result.success(result);
    }

    @Operation(summary = "切换营业状态", description = "商家切换店铺的营业/休息状态")
    @PutMapping("/my/status")
    public Result<Map<String, String>> updateShopStatus(@RequestHeader("Authorization") String authHeader,
                                                          @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, String> result = shopService.updateShopStatus(token, body.get("status"));
        return Result.success(result);
    }

    @Operation(summary = "商家后台统计", description = "获取商家后台的仪表盘统计数据")
    @GetMapping("/my/dashboard")
    public Result<DashboardStatsVO> getDashboard(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        DashboardStatsVO result = shopService.getDashboard(token);
        return Result.success(result);
    }

    @Operation(summary = "商家订单列表", description = "查看属于自己店铺的订单列表，可按状态筛选")
    @GetMapping("/my/orders")
    public Result<PageResult<OrderItemVO>> getShopOrders(@RequestHeader("Authorization") String authHeader,
                                                           @RequestParam(required = false) String status,
                                                           @RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        String token = authHeader.replace("Bearer ", "");
        PageResult<OrderItemVO> result = shopService.getShopOrders(token, page, size, status);
        return Result.success(result);
    }

}
