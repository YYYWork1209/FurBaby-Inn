package com.furbaby.furbaby.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVO {

    private Long orderId;
    private String orderNo;
    private String shopName;
    private String petName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal amount;
    private LocalDateTime createTime;
}
