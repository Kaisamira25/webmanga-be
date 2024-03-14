package com.alpha.lainovo.config;

import com.alpha.lainovo.service.ServiceInterface.GenerateVerifyCode;
import com.alpha.lainovo.service.VerificationCodeManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConfigGenerateCodeVerify {
    @Bean("EmailVerifyCode")
    @Primary
    public VerificationCodeManager codeEmail(@Qualifier("EmailCode")GenerateVerifyCode generateVerifyCode){
        return new VerificationCodeManager(generateVerifyCode);
    }
    @Bean("PasswordVerifyCode")
    public VerificationCodeManager codePassword(@Qualifier("PasswordCode") GenerateVerifyCode generateVerifyCode){
        return new VerificationCodeManager(generateVerifyCode);
    }
}
