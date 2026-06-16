package com.furbaby.furbaby.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_no", nullable = false, length = 32)
    private String paymentNo;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method", nullable = false, columnDefinition = "ENUM('wechat','alipay')")
    private PayMethod payMethod;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('pending','success','failed','closed')")
    private Status status;

    @Column(name = "qr_code", length = 500)
    private String qrCode;

    @Column(name = "pay_url", length = 500)
    private String payUrl;

    @Column(name = "expire_time")
    private LocalDateTime expireTime;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public enum PayMethod {
        wechat, alipay
    }

    public enum Status {
        pending, success, failed, closed
    }

    @PrePersist
    protected void onCreate() {
        if (status == null) status = Status.pending;
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
