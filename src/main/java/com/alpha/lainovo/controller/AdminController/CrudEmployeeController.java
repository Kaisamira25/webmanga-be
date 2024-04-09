package com.alpha.lainovo.controller.AdminController;

import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class CrudEmployeeController {
    private final EmployeeService employeeService;
    @Operation(description = "Create employee account to manager publication and something else", summary = "Create employee account",responses = {
            @ApiResponse(description = "Create successful",responseCode = "200"),
            @ApiResponse(description = "Create failure because doesn't understand the request", responseCode = "400"),
            @ApiResponse(description = "Create failure because the request unauthorized", responseCode = "401")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Integer employeeCreate = employeeService.createAccountEmployee(employeeDTO);
        if (employeeCreate == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Success create employee"));
        } else if (employeeCreate == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0,"Wrong password format"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(2,"Account name already exist"));
    }
}
