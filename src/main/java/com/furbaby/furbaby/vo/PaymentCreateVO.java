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
public class PaymentCreateVO {

    private Long paymentId;
    private String payNo;
    private BigDecimal amount;
    private String qrCode;
    private String payUrl;
    private LocalDateTime expireTime;
}
