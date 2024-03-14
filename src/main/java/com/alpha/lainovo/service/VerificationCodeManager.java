package com.alpha.lainovo.service;

import com.alpha.lainovo.service.ServiceInterface.GenerateVerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VerificationCodeManager {
    private final GenerateVerifyCode emailVerification;

    @Autowired
    public VerificationCodeManager(GenerateVerifyCode emailVerification){
        this.emailVerification = emailVerification;
    }
    public String generateCode(){
        return this.emailVerification.generateCode();
    }
    public Date codeExpiration(){
        return this.emailVerification.codeExpiration();
    }
}
