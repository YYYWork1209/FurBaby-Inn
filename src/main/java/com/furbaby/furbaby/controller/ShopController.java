package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.ScheduleDTO;
import com.furbaby.furbaby.dto.ScheduleUpdateDTO;
import com.furbaby.furbaby.dto.ShopRegisterDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IShopService;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.SchedulePublishVO;
import com.furbaby.furbaby.vo.ScheduleUpdateResultVO;
import com.furbaby.furbaby.vo.ShopDetailVO;
import com.furbaby.furbaby.vo.ShopRegisterVO;
import com.furbaby.furbaby.vo.ShopScheduleVO;
import com.furbaby.furbaby.vo.ShopVO;
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

}
