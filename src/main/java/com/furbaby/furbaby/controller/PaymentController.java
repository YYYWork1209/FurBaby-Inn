package com.furbaby.furbaby.controller;

import com.furbaby.furbaby.dto.PaymentCreateDTO;
import com.furbaby.furbaby.dto.RefundDTO;
import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.service.IPaymentService;
import com.furbaby.furbaby.vo.PaymentCreateVO;
import com.furbaby.furbaby.vo.PaymentStatusVO;
import com.furbaby.furbaby.vo.RefundVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "支付管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final IPaymentService paymentService;

    @Operation(summary = "创建支付单", description = "为指定订单创建支付单，生成支付二维码或链接")
    @PostMapping("/create")
    public Result<PaymentCreateVO> createPayment(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody PaymentCreateDTO createDTO) {
        String token = authHeader.replace("Bearer ", "");
        PaymentCreateVO result = paymentService.createPayment(token, createDTO);
        return Result.success(result);
    }

    @Operation(summary = "查询支付状态", description = "根据支付单ID查询支付状态及支付时间")
    @GetMapping("/status/{paymentId}")
    public Result<PaymentStatusVO> getPaymentStatus(@PathVariable Long paymentId) {
        PaymentStatusVO result = paymentService.getPaymentStatus(paymentId);
        return Result.success(result);
    }

    @Operation(summary = "申请退款", description = "为指定订单申请退款，订单主人或商家均可操作")
    @PostMapping("/refund")
    public Result<RefundVO> refund(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody RefundDTO refundDTO) {
        String token = authHeader.replace("Bearer ", "");
        RefundVO result = paymentService.refund(token, refundDTO);
        return Result.success(result);
    }

    @Operation(summary = "商家处理退款", description = "商家确认或拒绝退款申请，approve=同意退款并恢复档期，reject=拒绝退款恢复寄养")
    @PutMapping("/refund/{refundId}/process")
    public Result<Map<String, String>> processRefund(@RequestHeader("Authorization") String authHeader,
                                                       @PathVariable Long refundId,
                                                       @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, String> result = paymentService.processRefund(token, refundId, body.get("action"));
        return Result.success(result);
    }
}
