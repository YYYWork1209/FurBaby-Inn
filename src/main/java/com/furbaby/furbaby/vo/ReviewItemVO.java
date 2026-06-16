package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewItemVO {

    private Long reviewId;
    private Long userId;
    private String nickname;
    private String avatar;
    private Integer rating;
    private String content;
    private List<String> photos;
    private LocalDateTime createTime;
}
