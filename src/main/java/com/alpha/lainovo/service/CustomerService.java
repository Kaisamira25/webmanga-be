package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.ChangePasswordDTO;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Email;
import com.alpha.lainovo.repository.CustomerRepository;
import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.service.ServiceInterface.EmailInterface;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements CustomerInterface,CreateAndUpdateInterface<Integer, Customer> {
    private final CustomerRepository customerRepository;
    private final GetCustomerIdByRequestService getCustomerIdByRequestService;
    private final PasswordEncoder encoder;
    private final SendMailTemplateService sendMailTemplateService;
    private final EmailInterface emailInterface;
    @Qualifier("PasswordVerifyCode")
    private final VerificationCodeManager verificationCodeManager;

    @Autowired
    @Qualifier("CheckPassword")
    private final CheckStringInterface checkString;

    @Value("${email_root}")
    private String EMAIL_ROOT;

    public static final String TEMPLATE_VERIFY_CODE_SIGN_UP = "templateVerifyCodeRegister";

    public static String MESSAGE_NOTIFICATION = "Use this code to reset password: ";
    @Override
    public Object create(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public void update(Integer key, Customer entity) {
        customerRepository.save(entity);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findById(Integer userId) {
        Optional<Customer> user = customerRepository.findById(userId);
        return user.get();
    }

    @Override
    public Customer findByRefreshToken(String token) {
        return customerRepository.findByRefreshToken(token);
    }

    @Override
    public int changePassword(ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        Integer id = getCustomerIdByRequestService.getCustomerIdByRequest(request);

        Customer customer = customerRepository.findById(id).orElseThrow();

        if (!encoder.matches(changePasswordDTO.password(),customer.getPassword())){
            log.info("------> CustomerService: changePassword | New password does not match the current password");
            return 0;
        } else {
            if (!changePasswordDTO.newPassword().equals(changePasswordDTO.confirmPassword())){

                log.info("------> CustomerService: changePassword | New password does not match the password confirm");
                return 1;
            }
            customer.setPassword(encoder.encode(changePasswordDTO.newPassword()));
            update(customer.getCustomerId(), customer);
            log.info("------> CustomerService: changePassword | change password successfully");
            return 2;
        }
    }

    @Override
    public void resetPassword(String emailCustomer, String code) {
        Customer customer = findByEmail(emailCustomer);
        customer.setCustomerResetPasswordCode(code);
        customer.setCustomerResetPasswordCodeExpiration(verificationCodeManager.codeExpiration());
        update(customer.getCustomerId(),customer);
        Email email = new Email();
        email.setFrom(EMAIL_ROOT);
        email.setTo(customer.getEmail());
        email.setTitle("Email verification");
        email.setBody(sendMailTemplateService.sendMailWithTemplate(email.getTitle(),
                MESSAGE_NOTIFICATION + customer.getEmail(),
                code,
                TEMPLATE_VERIFY_CODE_SIGN_UP
                ));
        emailInterface.send(email);

    }

    @Override
    public Customer findByPasswordResetToken(String code) {
        log.info("Find Reset Password code: {}",customerRepository.findByCustomerResetPasswordCode(code));
        return customerRepository.findByCustomerResetPasswordCode(code);
    }

    @Transactional
    @Override
    public boolean resetAndCreateNewPassword(String code, String newPassword) {
        if (checkString.isStringValid(newPassword)){
            Customer customer = findByPasswordResetToken(code);
            customer.setPassword(encoder.encode(newPassword));
            update(customer.getCustomerId(),customer);
            return true;
        }
        return false;
    }

    @Override
    public Customer getCustomerInfo(HttpServletRequest request) {
        // Tìm khách hàng dựa trên customerId từ request
        Integer customerId = getCustomerIdByRequestService.getCustomerIdByRequest(request);
        Customer customer = findById(customerId);
        if (customer == null) {
            // Xử lý trường hợp không tìm thấy khách hàng
            log.error("Customer not found with id: {}", customerId);
            return null;
        }

        // Trả về thông tin của khách hàng
        log.info(">>>>>>> CustomerService:getCustomerInfo | Get info for Customer with email: {}", customer.getEmail());
        return customer;
    }


}
