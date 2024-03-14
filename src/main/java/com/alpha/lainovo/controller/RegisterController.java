package com.alpha.lainovo.controller;

import com.alpha.lainovo.dto.request.RegisterDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.User;
import com.alpha.lainovo.service.RegisterService;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.EmailInterface;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;
    private final EmailInterface email;
    private final CreateAndUpdateInterface<Integer, User> createUser;
    @Operation(description = "Register account for new user",summary = "Register",responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Email already exist", responseCode = "302"),
            @ApiResponse(description = "Password or email format is not correct", responseCode = "400")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        int status = registerService.register(registerDTO);
        if (status == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(0, "Email or password format is incorrect"));
        }else if (status == 1){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Message(1, "Register successfully", registerDTO));
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new Message(2, "Email already exists"));
    }
}
