package com.furbaby.furbaby.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusVO {

    private Long paymentId;
    private String status;
    private LocalDateTime payTime;
}
