package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.ScheduleDTO;
import com.furbaby.furbaby.dto.ScheduleUpdateDTO;
import com.furbaby.furbaby.dto.ShopRegisterDTO;
import com.furbaby.furbaby.dto.ShopUpdateDTO;
import com.furbaby.furbaby.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
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

public interface IShopService extends IService<Shop> {

    PageResult<ShopVO> listShops(String keyword, Integer page, Integer size, String sort);

    ShopDetailVO getShopDetail(Long id);

    ShopScheduleVO getShopSchedule(Long shopId, String startDate, String endDate);

    SchedulePublishVO publishSchedule(String token, ScheduleDTO scheduleDTO);

    ScheduleUpdateResultVO updateSchedule(String token, String date, ScheduleUpdateDTO updateDTO);

    ShopRegisterVO registerShop(String token, ShopRegisterDTO registerDTO);

    MerchantShopVO getMyShop(String token);

    MerchantShopVO updateMyShop(String token, ShopUpdateDTO updateDTO);

    Map<String, String> updateShopStatus(String token, String bizStatus);

    DashboardStatsVO getDashboard(String token);

    PageResult<OrderItemVO> getShopOrders(String token, Integer page, Integer size, String status);
}
