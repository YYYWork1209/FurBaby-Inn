package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.PaymentCreateDTO;
import com.furbaby.furbaby.dto.RefundDTO;
import com.furbaby.furbaby.vo.PaymentCreateVO;
import com.furbaby.furbaby.vo.PaymentStatusVO;
import com.furbaby.furbaby.vo.RefundVO;

import java.util.Map;

public interface IPaymentService {

    PaymentCreateVO createPayment(String token, PaymentCreateDTO createDTO);

    PaymentStatusVO getPaymentStatus(Long paymentId);

    RefundVO refund(String token, RefundDTO refundDTO);

    Map<String, String> processRefund(String token, Long refundId, String action);
}
