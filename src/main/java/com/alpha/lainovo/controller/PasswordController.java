package com.alpha.lainovo.controller;

import com.alpha.lainovo.dto.request.ChangePasswordDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.RefreshToken;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.service.VerificationCodeManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class PasswordController {
    private final CustomerInterface customerInterface;

    private final RefreshToken refreshToken;
    @Qualifier("PasswordVerifyCode")
    private final VerificationCodeManager verificationCodeManager;

    @Operation(description = "Change password", summary = "Change password", responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Invalid password", responseCode = "400") })
    @PatchMapping("/customer/change-password")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                            final HttpServletRequest request){
        int status = customerInterface.changePassword(changePasswordDTO, request);

        if (status == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(0, "Incorrect password !!!"));
        }
        if (status == 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(0, "New password does not match the confirm password"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Change password successfully"));
    }
    @Operation(description = "Forgot password", summary = "Forgot password", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Email does not exist", responseCode = "404")
    })
    @PostMapping("/customer/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody String email){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(email);
            String customerEmail = jsonNode.get("email").asText();

            Customer customer = customerInterface.findByEmail(customerEmail);
            if (customer == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message(0,"Email not found"));
            }
            customer.setCustomerResetPasswordCode(verificationCodeManager.generateCode());
            log.info("Customer: {}",verificationCodeManager.generateCode());

            customerInterface.resetPassword(customerEmail, customer.getCustomerResetPasswordCode());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Message(1, "Password reset code has been to your email"));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error: " + e.getMessage());
        }
    }

    @Operation(description = "Password reset", summary = "Verify the password code and change the new password", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Verification code does not exist", responseCode = "404"),
            @ApiResponse(description = "Maybe the password is not in the correct format", responseCode = "502") })
    @GetMapping("/customer/passwordReset")
    public ResponseEntity<?> passwordReset(@RequestParam("code") String code, @RequestHeader("password") String newPassword){
        Customer resetPasswordCode = customerInterface.findByPasswordResetToken(code);
        if (resetPasswordCode == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(1, "Code not found"));
        }
        boolean validateCodePassword = customerInterface.validateCodePassword(code, newPassword);
        if (validateCodePassword){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Message(1, "Password recovery successful"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new Message(0, "Password retrieval failed, please try again later (Maybe the password is not in the correct format)"));
        }
    }
}
