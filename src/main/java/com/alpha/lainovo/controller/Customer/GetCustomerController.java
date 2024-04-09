package com.alpha.lainovo.controller.Customer;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@SecurityRequirement(name = "bearerAuth")
public class GetCustomerController {
    private final CustomerInterface icus;
    @GetMapping("/{id}")
    @Operation(summary = "Find Customer by Id",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getCustomerbyID(@PathVariable("id") Integer id) {
        Customer customer=icus.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", customer));
    }
}
