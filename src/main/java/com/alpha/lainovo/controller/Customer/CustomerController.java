package com.alpha.lainovo.controller.Customer;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerInterface Icus;
    @GetMapping("/{id}")
    @Operation(summary = "Find Customer by Id",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getCustomerbyID(@PathVariable("id") Integer id) {
        Customer customer=Icus.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", Icus));
    }
}
