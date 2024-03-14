package com.alpha.lainovo.utilities.validate;

import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CheckEmail")
@Slf4j
public class CheckEmail implements CheckStringInterface {
    private static final String regex_email = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public boolean isStringValid(String rawEmail) {
        Pattern pattern = Pattern.compile(regex_email);
        Matcher matcher = pattern.matcher(rawEmail);
        log.info("CheckEmail: {}",rawEmail);
        return matcher.matches();
    }
}
