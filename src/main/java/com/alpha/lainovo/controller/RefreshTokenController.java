package com.alpha.lainovo.controller;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.RefreshToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class RefreshTokenController {
    private final RefreshToken refreshToken;
    @Operation(description = "Refresh token for an expired access token", responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Refresh token does not exist", responseCode = "404"),
            @ApiResponse(description = "The refresh token has expired", responseCode = "400")
    })
    @GetMapping("customers/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String status = refreshToken.generateAccessToken(request);

        if (status.equals("0")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Refresh token not found"));
        }else if (status.equals("1")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Refresh token expired"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Access token", status));
    }
}
