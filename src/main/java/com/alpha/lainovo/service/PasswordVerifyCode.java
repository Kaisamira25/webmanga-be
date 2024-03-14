package com.alpha.lainovo.service;

import com.alpha.lainovo.service.ServiceInterface.GenerateVerifyCode;
import com.alpha.lainovo.utilities.time.Time;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("PasswordCode")
public class PasswordVerifyCode implements GenerateVerifyCode {
    private static final int expiration_time = 10;
    @Override
    public String generateCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    @Override
    public Date codeExpiration() {
        return Time.getTheTimeWhenTokenExpiration(expiration_time);
    }
}
