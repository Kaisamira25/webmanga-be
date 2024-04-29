package com.alpha.lainovo.controller.Customer;

import com.alpha.lainovo.dto.request.RUpdateCustomerDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class GetCustomerController {
    private final CustomerInterface icus;
    @GetMapping("/{customerId}")
    @Operation(summary = "Find Customer by Id",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getCustomerbyID(@PathVariable("customerId") Integer customerId) {
        Customer customer=icus.findById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", customer));
    }

    @GetMapping("/all")
    @Operation(summary = "Find all Customers",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = icus.getAllCustomer();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", customers));
    }

    @Operation(summary = "Get Customer Info", description = "Get Customer Info", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Customer not found", responseCode = "404")})
    @GetMapping("/info")
    public ResponseEntity<Message> getCustomerInfo(HttpServletRequest request) {
        Customer customer = icus.getCustomerInfo(request);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Get customer info success", customer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Customer not found", null));
    }

    @PutMapping("/updateStatusCustomer/{customerId}")
    @Operation(summary = "Update Customer Status", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Customer not found", responseCode = "404")})
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Message> updateCustomerStatus(@PathVariable("customerId") Integer customerId, @RequestBody RUpdateCustomerDTO rUpdateCustomerDTO) {
        Customer updatedStatusCustomer = icus.updateCustomerStatus(customerId, rUpdateCustomerDTO);
        if (updatedStatusCustomer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Update customer status success", updatedStatusCustomer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Updated fail, Customer not found"));
    }

}
