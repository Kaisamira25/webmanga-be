package com.alpha.lainovo.utilities.validate;

import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CheckEmail")
@Slf4j
public class CheckEmail implements CheckStringInterface {
    private static final String regex_email = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    @Override
    public boolean isStringValid(String rawEmail) {
        // alternative to Pattern.CASE_INSENSITIVE is to include a-z interval in your regex
        Pattern pattern = Pattern.compile(regex_email, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(rawEmail);
        log.info("CheckEmail: {}",rawEmail);
        return matcher.matches();
    }
}
