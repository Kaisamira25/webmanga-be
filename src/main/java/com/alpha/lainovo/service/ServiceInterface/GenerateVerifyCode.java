package com.alpha.lainovo.service.ServiceInterface;

import java.util.Date;

public interface GenerateVerifyCode {
    String generateCode();

    Date codeExpiration();
}
