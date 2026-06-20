package com.furbaby.furbaby.service;

import com.furbaby.furbaby.dto.PaymentCreateDTO;
import com.furbaby.furbaby.vo.PaymentCreateVO;

public interface IPaymentService {

    PaymentCreateVO createPayment(String token, PaymentCreateDTO createDTO);
}
