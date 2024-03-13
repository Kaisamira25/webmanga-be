package com.alpha.lainovo.utilities.validate;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CheckPassword")
@Primary
public class CheckPassword implements CheckStringInterface{
    private final String regex_password = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

    @Override
    public boolean isStringValid(String rawPassword) {
        Pattern pattern = Pattern.compile(regex_password);
        Matcher matcher = pattern.matcher(rawPassword);
        return matcher.matches();
    }
}
