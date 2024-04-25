package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.ChangePasswordDTO;
import com.alpha.lainovo.dto.request.RUpdateCustomerDTO;
import com.alpha.lainovo.model.Customer;
import jakarta.servlet.http.HttpServletRequest;

public interface CustomerInterface {
    Customer findByEmail(String email);

    Customer findById(Integer customerId);

    Customer findByRefreshToken(String token);

    int changePassword(ChangePasswordDTO changePasswordDTO, HttpServletRequest request);

    void resetPassword(String email, String code);

    Customer findByPasswordResetToken(String code);

    Customer getCustomerInfo(HttpServletRequest request);
    Customer updateCustomerStatus(Integer customerId, RUpdateCustomerDTO rUpdateCustomerDTO);

    boolean resetAndCreateNewPassword(String code, String newPassword);
}
