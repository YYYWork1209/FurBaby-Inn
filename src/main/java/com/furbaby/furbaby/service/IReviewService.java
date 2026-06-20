package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.ReviewSubmitDTO;
import com.furbaby.furbaby.vo.ReviewPageVO;
import com.furbaby.furbaby.vo.ReviewVO;

public interface IReviewService {

    ReviewVO submitReview(String token, ReviewSubmitDTO submitDTO);

    ReviewPageVO getShopReviews(Long shopId, Integer page, Integer size);
}
