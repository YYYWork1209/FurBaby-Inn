package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furbaby.furbaby.dto.ScheduleDTO;
import com.furbaby.furbaby.dto.ScheduleUpdateDTO;
import com.furbaby.furbaby.dto.ShopRegisterDTO;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.ShopSchedule;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.mapper.ShopScheduleMapper;
import com.furbaby.furbaby.service.IShopService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.SchedulePublishVO;
import com.furbaby.furbaby.vo.ScheduleUpdateResultVO;
import com.furbaby.furbaby.vo.ScheduleVO;
import com.furbaby.furbaby.vo.ShopDetailVO;
import com.furbaby.furbaby.vo.ShopRegisterVO;
import com.furbaby.furbaby.vo.ShopScheduleVO;
import com.furbaby.furbaby.vo.ShopVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    private final ShopScheduleMapper shopScheduleMapper;
    private final JWTUtils jwtUtils;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PageResult<ShopVO> listShops(String keyword, Integer page, Integer size, String sort) {
        LambdaQueryWrapper<Shop> wrapper = Wrappers.<Shop>lambdaQuery()
                .eq(Shop::getStatus, com.furbaby.furbaby.enums.ShopStatus.approved);

        if (keyword != null && !keyword.isBlank()) {
            wrapper.and(w -> w
                    .like(Shop::getName, keyword)
                    .or()
                    .like(Shop::getTags, keyword)
                    .or()
                    .like(Shop::getDescription, keyword)
                    .or()
                    .like(Shop::getAddress, keyword));
        }

        if ("rating".equals(sort)) {
            wrapper.orderByDesc(Shop::getRating);
        } else if ("price".equals(sort)) {
            wrapper.orderByAsc(Shop::getPrice);
        } else {
            wrapper.orderByDesc(Shop::getCreateTime);
        }

        long total = this.count(wrapper);
        long pages = (total + size - 1) / size;
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + "," + size);

        List<Shop> shopList = this.list(wrapper);
        List<ShopVO> records = shopList.stream()
                .map(this::toShopVO)
                .collect(Collectors.toList());

        return PageResult.<ShopVO>builder()
                .total(total)
                .pages(pages)
                .records(records)
                .build();
    }

    @Override
    public ShopDetailVO getShopDetail(Long id) {
        Shop shop = this.getOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, id));
        if (shop == null) {
            throw new NoRegisterException("商家不存在");
        }
        return toShopDetailVO(shop);
    }

    @Override
    public ShopScheduleVO getShopSchedule(Long shopId, String startDate, String endDate) {
        LambdaQueryWrapper<ShopSchedule> wrapper = Wrappers.<ShopSchedule>lambdaQuery()
                .eq(ShopSchedule::getShopId, shopId);

        if (startDate != null && !startDate.isBlank()) {
            wrapper.ge(ShopSchedule::getDate, LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isBlank()) {
            wrapper.le(ShopSchedule::getDate, LocalDate.parse(endDate));
        }
        wrapper.orderByAsc(ShopSchedule::getDate);

        List<ShopSchedule> schedules = shopScheduleMapper.selectList(wrapper);
        List<ScheduleVO> scheduleVOs = schedules.stream()
                .map(s -> ScheduleVO.builder()
                        .date(s.getDate())
                        .available(s.getAvailable())
                        .price(s.getPrice())
                        .build())
                .collect(Collectors.toList());

        return ShopScheduleVO.builder()
                .shopId(shopId)
                .schedules(scheduleVOs)
                .build();
    }

    @Override
    public SchedulePublishVO publishSchedule(String token, ScheduleDTO scheduleDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        Shop shop = this.getOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getUserId, userId));
        if (shop == null) {
            throw new NoRegisterException("商家不存在");
        }

        int count = 0;
        for (ScheduleDTO.ScheduleItem item : scheduleDTO.getDates()) {
            LocalDate date = LocalDate.parse(item.getDate());
            ShopSchedule existing = shopScheduleMapper.selectOne(
                    Wrappers.<ShopSchedule>lambdaQuery()
                            .eq(ShopSchedule::getShopId, shop.getId())
                            .eq(ShopSchedule::getDate, date));

            if (existing != null) {
                existing.setAvailable(item.getAvailable());
                existing.setPrice(item.getPrice());
                existing.setUpdateTime(LocalDateTime.now());
                shopScheduleMapper.updateById(existing);
            } else {
                ShopSchedule schedule = ShopSchedule.builder()
                        .shopId(shop.getId())
                        .date(date)
                        .available(item.getAvailable())
                        .price(item.getPrice())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                shopScheduleMapper.insert(schedule);
            }
            count++;
        }

        return SchedulePublishVO.builder().success(true).count(count).build();
    }

    @Override
    public ScheduleUpdateResultVO updateSchedule(String token, String date, ScheduleUpdateDTO updateDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));
        Shop shop = this.getOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getUserId, userId));
        if (shop == null) {
            throw new NoRegisterException("商家不存在");
        }

        LocalDate targetDate = LocalDate.parse(date);
        ShopSchedule schedule = shopScheduleMapper.selectOne(
                Wrappers.<ShopSchedule>lambdaQuery()
                        .eq(ShopSchedule::getShopId, shop.getId())
                        .eq(ShopSchedule::getDate, targetDate));

        if (schedule == null) {
            throw new NoRegisterException("该日期暂无档期");
        }

        schedule.setAvailable(schedule.getAvailable() + updateDTO.getDelta());
        schedule.setUpdateTime(LocalDateTime.now());
        shopScheduleMapper.updateById(schedule);

        return ScheduleUpdateResultVO.builder().success(true).available(schedule.getAvailable()).build();
    }

    @Override
    public ShopRegisterVO registerShop(String token, ShopRegisterDTO registerDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Shop existing = this.getOne(Wrappers.<Shop>lambdaQuery().eq(Shop::getUserId, userId));
        if (existing != null) {
            throw new NoRegisterException("该用户已注册商家");
        }

        try {
            Shop shop = Shop.builder()
                    .userId(userId)
                    .name(registerDTO.getName())
                    .phone(registerDTO.getPhone())
                    .address(registerDTO.getAddress())
                    .description(registerDTO.getDescription())
                    .tags(registerDTO.getTags() != null ? objectMapper.writeValueAsString(registerDTO.getTags()) : null)
                    .services(registerDTO.getServices() != null ? objectMapper.writeValueAsString(registerDTO.getServices()) : null)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            this.save(shop);

            return ShopRegisterVO.builder()
                    .shopId(shop.getId())
                    .status(shop.getStatus().name())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("商家注册失败", e);
        }
    }

    private ShopVO toShopVO(Shop shop) {
        return ShopVO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .avatar(shop.getAvatar())
                .rating(shop.getRating())
                .price(shop.getPrice())
                .tags(parseJsonList(shop.getTags()))
                .address(shop.getAddress())
                .distance(null)
                .build();
    }

    private ShopDetailVO toShopDetailVO(Shop shop) {
        return ShopDetailVO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .avatar(shop.getAvatar())
                .photos(parseJsonList(shop.getPhotos()))
                .rating(shop.getRating())
                .price(shop.getPrice())
                .tags(parseJsonList(shop.getTags()))
                .address(shop.getAddress())
                .phone(shop.getPhone())
                .description(shop.getDescription())
                .services(parseJsonList(shop.getServices()))
                .notice(shop.getNotice())
                .build();
    }

    private List<String> parseJsonList(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
