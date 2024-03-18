package com.alpha.lainovo.controller;

import com.alpha.lainovo.dto.request.LoginDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;
    @Operation(description = "Successful login return an accessToken and refreshToken",
    summary = "Login",
    responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "User doesn't exist", responseCode = "404"),
            @ApiResponse(description = "Invalid for the case", responseCode = "400")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request){
        int checkUserLogin = authService.validate(loginDTO);
        if (checkUserLogin == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message(0, "User not found, Error login"));
        }else if (checkUserLogin == 1){
            return ResponseEntity.ok(new Message(1, "Login successfully", authService.login(loginDTO, request)));
        }else if (checkUserLogin == 2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(0, "Your email hasn't verify yet"));
        }else if (checkUserLogin == 3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(0, "This account has been locked"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message(0, "Invalid email or password"));
    }
    @Operation(description = "Successful logout set refresh_toke to null",
    summary = "Logout",
    parameters = {
            @Parameter(in = ParameterIn.HEADER,
            name = "Authorization",
            description = "JWT Token",
            required = true,
            schema = @Schema(type = "string"))
    },
    responses = {
            @ApiResponse(description = "Success", responseCode = "200")
    })
    @PatchMapping("/logout")
    public ResponseEntity<?> logout(final HttpServletRequest request){
        authService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Logout Successful"));
    }

}
