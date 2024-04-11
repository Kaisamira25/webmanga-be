package com.alpha.lainovo.controller.AdminController;

import com.alpha.lainovo.dto.request.EmployeeLoginDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/admin/")
public class AdminController {
    private final EmployeeService employeeService;
    @Operation(description = "Successful login return an accessToken and refreshToken",
            summary = "Login",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Admin doesn't exist", responseCode = "404"),
                    @ApiResponse(description = "Invalid for the case", responseCode = "400")
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("Login admin start");
        Integer checkAdminLogin = employeeService.validate(employeeLoginDTO);
        if (checkAdminLogin == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0,"Not found account admin"));
        } else if (checkAdminLogin == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1,"Login success",employeeService.loginAccountAdmin(employeeLoginDTO)));
        } else if (checkAdminLogin == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Account blocked"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message(0, "Invalid account or password"));
    }
}
