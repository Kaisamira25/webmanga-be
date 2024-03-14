package com.alpha.lainovo.service;

import com.alpha.lainovo.service.ServiceInterface.GenerateVerifyCode;
import com.alpha.lainovo.utilities.time.Time;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("EmailCode")
@Primary
public class EmailVerifyCode implements GenerateVerifyCode {
    public static final int expiration_time = 10;
    @Override
    public String generateCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    @Override
    public Date codeExpiration() {
        return Time.getTheTimeWhenTokenExpiration(expiration_time);
    }
}
