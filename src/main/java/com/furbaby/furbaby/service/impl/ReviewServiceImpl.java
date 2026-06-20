package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.Review;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.ReviewMapper;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.service.IReviewService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {

    private final ReviewMapper reviewMapper;
    private final OrderMapper orderMapper;
    private final ShopMapper shopMapper;
    private final JWTUtils jwtUtils;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReviewVO submitReview(String token, ReviewSubmitDTO submitDTO) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, submitDTO.getOrderId()));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new NoRegisterException("无权评价他人订单");
        }
        if (order.getStatus() != OrderStatus.completed) {
            throw new NoRegisterException("仅已完成订单可评价");
        }

        Review existing = reviewMapper.selectOne(Wrappers.<Review>lambdaQuery()
                .eq(Review::getOrderId, submitDTO.getOrderId()));
        if (existing != null) {
            throw new NoRegisterException("该订单已评价");
        }

        String photosJson = null;
        if (submitDTO.getPhotos() != null && !submitDTO.getPhotos().isEmpty()) {
            try {
                photosJson = objectMapper.writeValueAsString(submitDTO.getPhotos());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("照片格式错误", e);
            }
        }

        Review review = Review.builder()
                .orderId(order.getId())
                .shopId(order.getShopId())
                .userId(userId)
                .rating(submitDTO.getRating())
                .content(submitDTO.getContent())
                .photos(photosJson)
                .createTime(LocalDateTime.now())
                .build();
        reviewMapper.insert(review);

        recalcShopRating(order.getShopId());

        return ReviewVO.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .createTime(review.getCreateTime())
                .build();
    }

    private void recalcShopRating(Long shopId) {
        Double avgRating = reviewMapper.selectList(
                Wrappers.<Review>lambdaQuery().eq(Review::getShopId, shopId))
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(5.0);
        avgRating = Math.round(avgRating * 10.0) / 10.0;
        Shop shop = shopMapper.selectById(shopId);
        if (shop != null) {
            shop.setRating(avgRating);
            shopMapper.updateById(shop);
        }
    }
}
