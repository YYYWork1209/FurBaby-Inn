package com.furbaby.furbaby.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateVO {

    private Long orderId;
    private String orderNo;
    private String status;
    private BigDecimal amount;
    private LocalDateTime createTime;
}
