package com.alpha.lainovo.controller;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/info")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerInterface customerInterface;

    @Operation(summary = "Get Customer Info", description = "Get Customer Info", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Customer not found", responseCode = "404")})
    @GetMapping()
    public ResponseEntity<Message> getCustomerInfo(HttpServletRequest request) {
        Customer customer = customerInterface.getCustomerInfo(request);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Get customer info success", customer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Customer not found", null));
    }

}
