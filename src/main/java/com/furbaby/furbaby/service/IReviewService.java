package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.vo.BoardingPhotoVO;
import com.furbaby.furbaby.vo.PageResult;
import com.furbaby.furbaby.vo.ReviewItemVO;
import com.furbaby.furbaby.vo.ReviewPageVO;
import com.furbaby.furbaby.vo.ReviewVO;
import org.springframework.web.multipart.MultipartFile;

public interface IReviewService {

    ReviewVO submitReview(String token, ReviewSubmitDTO submitDTO);

    ReviewPageVO getShopReviews(Long shopId, Integer page, Integer size);

    PageResult<ReviewItemVO> getMyReviews(String token, Integer page, Integer size);

    BoardingPhotoVO uploadPhoto(String token, Long orderId, MultipartFile file);
}
