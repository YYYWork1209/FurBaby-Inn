package com.furbaby.furbaby.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.entity.BoardingPhoto;
import com.furbaby.furbaby.entity.Order;
import com.furbaby.furbaby.entity.Review;
import com.furbaby.furbaby.entity.Shop;
import com.furbaby.furbaby.entity.User;
import com.furbaby.furbaby.enums.OrderStatus;
import com.furbaby.furbaby.exception.NoRegisterException;
import com.furbaby.furbaby.mapper.BoardingPhotoMapper;
import com.furbaby.furbaby.mapper.OrderMapper;
import com.furbaby.furbaby.mapper.ReviewMapper;
import com.furbaby.furbaby.mapper.ShopMapper;
import com.furbaby.furbaby.mapper.UserMapper;
import com.furbaby.furbaby.service.IReviewService;
import com.furbaby.furbaby.utils.JWTUtils;
import com.furbaby.furbaby.vo.BoardingPhotoVO;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.ReviewItemVO;
import com.furbaby.furbaby.vo.ReviewPageVO;
import com.furbaby.furbaby.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {

    private final ReviewMapper reviewMapper;
    private final OrderMapper orderMapper;
    private final ShopMapper shopMapper;
    private final UserMapper userMapper;
    private final BoardingPhotoMapper boardingPhotoMapper;
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

    @Override
    public ReviewPageVO getShopReviews(Long shopId, Integer page, Integer size) {
        LambdaQueryWrapper<Review> wrapper = Wrappers.<Review>lambdaQuery()
                .eq(Review::getShopId, shopId)
                .orderByDesc(Review::getCreateTime);

        long total = reviewMapper.selectCount(wrapper);
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + "," + size);

        List<Review> reviews = reviewMapper.selectList(wrapper);

        Double avgRating = reviewMapper.selectList(
                Wrappers.<Review>lambdaQuery().eq(Review::getShopId, shopId))
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        avgRating = Math.round(avgRating * 10.0) / 10.0;

        List<ReviewItemVO> records = reviews.stream().map(r -> {
            User user = userMapper.selectById(r.getUserId());
            return ReviewItemVO.builder()
                    .reviewId(r.getId())
                    .userId(r.getUserId())
                    .nickname(user != null ? user.getNickname() : "匿名用户")
                    .avatar(user != null ? user.getAvatar() : null)
                    .rating(r.getRating())
                    .content(r.getContent())
                    .photos(parseJsonList(r.getPhotos()))
                    .createTime(r.getCreateTime())
                    .build();
        }).collect(Collectors.toList());

        return ReviewPageVO.builder()
                .total(total)
                .avgRating(avgRating)
                .records(records)
                .build();
    }

    @Override
    public PageResult<ReviewItemVO> getMyReviews(String token, Integer page, Integer size) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        LambdaQueryWrapper<Review> wrapper = Wrappers.<Review>lambdaQuery()
                .eq(Review::getUserId, userId)
                .orderByDesc(Review::getCreateTime);

        long total = reviewMapper.selectCount(wrapper);
        long pages = (total + size - 1) / size;
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + "," + size);

        List<Review> reviews = reviewMapper.selectList(wrapper);

        User user = userMapper.selectById(userId);
        String nickname = user != null ? user.getNickname() : "匿名用户";
        String avatar = user != null ? user.getAvatar() : null;

        List<ReviewItemVO> records = reviews.stream().map(r -> ReviewItemVO.builder()
                .reviewId(r.getId())
                .userId(r.getUserId())
                .nickname(nickname)
                .avatar(avatar)
                .rating(r.getRating())
                .content(r.getContent())
                .photos(parseJsonList(r.getPhotos()))
                .createTime(r.getCreateTime())
                .build()).collect(Collectors.toList());

        return PageResult.<ReviewItemVO>builder()
                .total(total)
                .pages(pages)
                .records(records)
                .build();
    }

    @Override
    public BoardingPhotoVO uploadPhoto(String token, Long orderId, MultipartFile file) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromToken(token));

        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getId, orderId));
        if (order == null) {
            throw new NoRegisterException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new NoRegisterException("无权操作他人订单");
        }

        String url = "https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/boarding/"
                + UUID.randomUUID().toString().substring(0, 8) + ".jpg";

        BoardingPhoto photo = BoardingPhoto.builder()
                .orderId(orderId)
                .userId(userId)
                .url(url)
                .uploadTime(LocalDateTime.now())
                .build();
        boardingPhotoMapper.insert(photo);

        return BoardingPhotoVO.builder()
                .photoId(photo.getId())
                .url(photo.getUrl())
                .uploadTime(photo.getUploadTime())
                .description(photo.getDescription())
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
