package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.RegisterDTO;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Email;
import com.alpha.lainovo.service.ServiceInterface.*;
import com.alpha.lainovo.utilities.time.Time;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterService implements SendMail<Customer> {
    private final OtpVerificationServiceInterface otpVerificationServiceInterface;
    private final CustomerService customerService;
    private final CreateAndUpdateInterface<Integer, Customer> createUser;
    private final PasswordEncoder passwordEncoder;
    private final EmailInterface emailInterface;
    @Autowired
    @Qualifier("CheckPassword")
    private final CheckStringInterface checkPasswordFormat;
    @Autowired
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
        if (customerService.findByEmail(registerDTO.email()) == null) {
            if (!checkPasswordFormat.isStringValid(registerDTO.password())
            || !checkEmailFormat.isStringValid(registerDTO.email())) {
               return 0; // Wrong format email or password
            }
            Customer customer = new Customer();
            customer.setEmail(registerDTO.email());
            customer.setFullName(registerDTO.fullName());
            customer.setPassword(passwordEncoder.encode(registerDTO.password()));
            customer.setCreatedAt(Time.getTheTimeRightNow());
            customer.setCustomerEmailVerifyCode(verificationCodeManager.generateCode());
            customer.setCustomerEmailVerifyCodeExpiration(verificationCodeManager.codeExpiration());
            log.info("------> RegisterService | register: register with email {}", customer.getEmail());
            createUser.create(customer);
            sendMail(customer, customer.getCustomerEmailVerifyCode());
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
            Customer customer = customerService.findByEmail(email);
            customer.setIsVerify(true);
            customerService.update(customer.getCustomerId(), customer);
            log.info("------> RegisterService | verify: User verified with email {}", email);
        }else {
            log.info("------> RegisterService | verify: Incorrect OTP for email {}", email);
        }
        return isVerified;
    }
    @Override
    public void sendMail(Customer customer, String code) {
//       String paramCode = url + "/api/v1/auth/verify?code=" + content.getUserVerifyCode();
       log.info("------> RegisterService | sendMail: Code for verify {} ", code);
       var email = new Email();
       email.setFrom(email_root);
       email.setTo(customer.getEmail());
       email.setTitle("Email Verification");
       email.setBody(sendMailTemplateService.sendMailWithTemplate(
               email.getTitle(),
               message_notification + customer.getEmail(),
               code,
               template_verify_code_register
       ));
       emailInterface.send(email);
    }
}
