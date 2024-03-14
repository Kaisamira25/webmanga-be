package com.alpha.lainovo.service.ServiceInterface;

public interface OtpVerificationServiceInterface {
    boolean verify(String email, String otp);
}
