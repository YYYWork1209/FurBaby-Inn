package com.furbaby.furbaby.vo;

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
public class OrderDetailVO {

    private Long orderId;
    private String orderNo;
    private String shopName;
    private String petName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal amount;
    private LocalDateTime createTime;
    private Long shopId;
    private Long petId;
    private String remark;
    private LocalDateTime payTime;
    private LocalDateTime cancelTime;
}
