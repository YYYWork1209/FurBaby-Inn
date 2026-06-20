package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IReviewService;
import com.furbaby.furbaby.vo.ReviewVO;
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
}
