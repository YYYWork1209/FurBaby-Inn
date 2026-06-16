package com.furbaby.furbaby.notify.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyItemVO {

    private Long id;
    private String type;
    private String title;
    private String content;
    private Boolean read;
    private LocalDateTime createTime;
}
