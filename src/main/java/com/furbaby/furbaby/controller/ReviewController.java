package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IReviewService;
import com.furbaby.furbaby.vo.BoardingPhotoVO;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.ReviewItemVO;
import com.furbaby.furbaby.vo.ReviewPageVO;
import com.furbaby.furbaby.vo.ReviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "评价管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final IReviewService reviewService;

    @Operation(summary = "提交评价", description = "对已完成订单进行评分和评价，每个订单仅可评价一次")
    @PostMapping("/submit")
    public Result<ReviewVO> submitReview(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody ReviewSubmitDTO submitDTO) {
        String token = authHeader.replace("Bearer ", "");
        ReviewVO result = reviewService.submitReview(token, submitDTO);
        return Result.success(result);
    }

    @Operation(summary = "商家评价列表", description = "分页查询指定商家的评价列表及平均评分")
    @GetMapping("/list/{shopId}")
    public Result<ReviewPageVO> getShopReviews(@PathVariable Long shopId,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        ReviewPageVO result = reviewService.getShopReviews(shopId, page, size);
        return Result.success(result);
    }

    @Operation(summary = "我的评价", description = "分页查询当前用户的所有评价记录")
    @GetMapping("/my-reviews")
    public Result<PageResult<ReviewItemVO>> getMyReviews(@RequestHeader("Authorization") String authHeader,
                                                           @RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        String token = authHeader.replace("Bearer ", "");
        PageResult<ReviewItemVO> result = reviewService.getMyReviews(token, page, size);
        return Result.success(result);
    }

    @Operation(summary = "上传寄养照片", description = "为指定订单上传寄养期间的照片")
    @PostMapping("/upload-photo")
    public Result<BoardingPhotoVO> uploadPhoto(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam Long orderId,
                                                @RequestParam("file") MultipartFile file) {
        String token = authHeader.replace("Bearer ", "");
        BoardingPhotoVO result = reviewService.uploadPhoto(token, orderId, file);
        return Result.success(result);
    }

    @Operation(summary = "获取寄养照片", description = "根据订单ID获取寄养期间上传的所有照片")
    @GetMapping("/photos/{orderId}")
    public Result<List<BoardingPhotoVO>> getPhotos(@PathVariable Long orderId) {
        List<BoardingPhotoVO> result = reviewService.getPhotos(orderId);
        return Result.success(result);
    }
}
