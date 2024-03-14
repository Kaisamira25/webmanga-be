package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.RegisterDTO;
import com.alpha.lainovo.model.Email;
import com.alpha.lainovo.model.User;
import com.alpha.lainovo.service.ServiceInterface.*;
import com.alpha.lainovo.utilities.time.Time;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService implements SendMail<User> {
    private final OtpVerificationServiceInterface otpVerificationServiceInterface;
    private final UserService userService;
    private final CreateAndUpdateInterface<Integer, User> createUser;
    private final EmailInterface emailInterface;
    @Qualifier("CheckPassword")
    private final CheckStringInterface checkPasswordFormat;
    @Qualifier("CheckEmail")
    private final CheckStringInterface checkEmailFormat;
    @Qualifier("EmailVerifyCode")
    private final VerificationCodeManager verificationCodeManager;
    @Value("${email_root}")
    private String email_root;
    private final SendMailTemplateService sendMailTemplateService;
    private static final String template_verify_code_register = "templateVerifyCodeRegister";
    private static String message_notification = "Use the code to verify this email: ";
    @Transactional
    public Integer register(RegisterDTO registerDTO, HttpServletRequest request){
        if (userService.findByEmail(registerDTO.email()) == null) {
            if (!checkPasswordFormat.isStringValid(registerDTO.password())
            || !checkEmailFormat.isStringValid(registerDTO.email())) {
                log.info("Wrong Format {}", checkPasswordFormat.isStringValid(registerDTO.password()));
                log.info("Wrong Format {}", checkEmailFormat.isStringValid(registerDTO.email()));
               return 0; // Wrong format email or password
            }
            User user = new User();
            user.setEmail(registerDTO.email());
            user.setFullName(registerDTO.fullName());
            user.setPassword(registerDTO.password());
            user.setCreatedAt(Time.getTheTimeRightNow());
            user.setUserVerifyCode(verificationCodeManager.generateCode());
            user.setUserVerifyCodeExpirationTime(verificationCodeManager.codeExpiration());
            log.info("------> RegisterService | register: register with email {}",user.getEmail());
            createUser.create(user);
            sendMail(user, user.getUserVerifyCode());

            request.getSession().setAttribute("email",registerDTO.email());
            return 1; // Create successful
        }else {
             return 2; // Email already exists
        }
    }
    @Transactional
    public boolean verify(String email, String otp){
        boolean isVerified = otpVerificationServiceInterface.verify(email, otp);
        if (isVerified){
            User user = userService.findByEmail(email);
            user.setIsVerify(true);
            userService.update(user.getUserid(), user);
            log.info("------> RegisterService | verify: User verified with email {}", email);
        }else {
            log.info("------> RegisterService | verify: Incorrect OTP for email {}", email);
        }
        return isVerified;
    }
    @Override
    public void sendMail(User user, String code) {
//       String paramCode = url + "/api/v1/auth/verify?code=" + content.getUserVerifyCode();
       log.info("------> RegisterService | sendMail: Code for verify {} ", code);
       var email = new Email();
       email.setFrom(email_root);
       email.setTo(user.getEmail());
       email.setTitle("Email Verification");
       email.setBody(sendMailTemplateService.sendMailWithTemplate(
               email.getTitle(),
               message_notification + user.getEmail(),
               code,
               template_verify_code_register
       ));
       emailInterface.send(email);
    }
}
