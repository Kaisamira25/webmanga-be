package com.alpha.lainovo.utilities.validate;

import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CheckPassword")
@Primary
@Slf4j
public class CheckPassword implements CheckStringInterface {
    private static final String regex_password = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    @Override
    public boolean isStringValid(String rawPassword) {
        Pattern pattern = Pattern.compile(regex_password);
        Matcher matcher = pattern.matcher(rawPassword);
        return matcher.matches();
    }
}
