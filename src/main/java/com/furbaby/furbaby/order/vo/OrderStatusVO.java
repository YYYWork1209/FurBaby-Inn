package com.furbaby.furbaby.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusVO {

    private Long orderId;
    private String status;
    private List<StatusStepVO> timeline;
}
