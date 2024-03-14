package com.alpha.lainovo.service;

import com.alpha.lainovo.model.User;
import com.alpha.lainovo.service.ServiceInterface.OtpVerificationServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpVerificationService implements OtpVerificationServiceInterface {
    private final UserService userService;

    @Override
    public boolean verify(String email, String otp) {
        User user = userService.findByEmail(email);
        if (user == null){
            return false;
        }
        return user.getUserVerifyCode().equals(otp);
    }
}
