package com.alpha.lainovo.utilities.validate;

import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CheckEmail")
public class CheckEmail implements CheckStringInterface {
    private static final String regex_email = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public boolean isStringValid(String rawPassword) {
        Pattern pattern = Pattern.compile(regex_email);
        Matcher matcher = pattern.matcher(rawPassword);
        return matcher.matches();
    }
}
