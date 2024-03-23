package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.ServiceInterface.OtpVerificationServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpVerificationService implements OtpVerificationServiceInterface {
    private final CustomerService customerService;

    @Override
    public boolean verify(String email, String otp) {
        Customer customer = customerService.findByEmail(email);
        if (customer == null){
            return false;
        }
        return customer.getCustomerEmailVerifyCode().equals(otp);
    }
}
