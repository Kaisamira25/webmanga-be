package com.alpha.lainovo.controller.AdminController;

import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.RAdminRoleDTO;
import com.alpha.lainovo.dto.request.REmployeeUpdateAndDeleteDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.EmployeeService;
import com.alpha.lainovo.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class CrudEmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/all")
    @SecurityRequirement(name="bearerAuth")
    @Operation(summary = "Find All Account",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getAllAccountEmployee() {
        List<RAdminRoleDTO> employees = employeeServiceImpl.getAllAccount();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", employees));
    }

    @GetMapping("/search/{accountName}")
    @SecurityRequirement(name="bearerAuth")
    @Operation(summary = "Find a Account with the accountName",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Account not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAccountByAccountName(@PathVariable("accountName") String accountName) {
        List<RAdminRoleDTO> account = employeeServiceImpl.getAccountByAccountName(accountName);
        if (account != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", account));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Account dose not exist"));

    }

    @Operation(description = "Create employee account to manager publication and something else", summary = "Create employee account",responses = {
            @ApiResponse(description = "Create successful",responseCode = "200"),
            @ApiResponse(description = "Create failure because doesn't understand the request", responseCode = "400"),
            @ApiResponse(description = "Create failure because the request unauthorized", responseCode = "401")
    })
    @PostMapping("/create")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Integer employeeCreate = employeeService.createAccountEmployee(employeeDTO);
        if (employeeCreate == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Success create employee"));
        } else if (employeeCreate == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0,"Wrong password format"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(2,"Account name already exist"));
    }

    @Operation(summary = "Update an Employee", description = "When the Employee is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Employee not found", responseCode = "400")})
    @PutMapping("/update/{accountName}")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> updateAccountEmployee(@PathVariable("accountName") String accountName, @RequestBody REmployeeUpdateAndDeleteDTO employeeDTO) {
        Admin employeeUpdate = employeeServiceImpl.updateEmployeeAccountName(accountName, employeeDTO);
        if (employeeUpdate != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Updated successfully", employeeUpdate));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Updated fail, Employee does not exist"));
    }

    @Operation(summary = "Delete an Employee", description = "When the Employee is successfully deleted, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Employee not found", responseCode = "400")})
    @DeleteMapping("/delete/{accountName}")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<?> deleteAccountEmployee(@PathVariable("accountName") String accountName) {
        Admin employeeDelete = employeeServiceImpl.findAdminAccountByAccountName(accountName);
        if (employeeDelete != null) {
            employeeServiceImpl.deleteEmployeeByAccountName(accountName);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Deleted fail, Employee does not exist"));
    }

}
