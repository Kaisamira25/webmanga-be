package com.alpha.lainovo.controller.PromotionalGiff;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.service.ServiceInterface.PromotionalGiftInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/gift")
@RequiredArgsConstructor
public class GetPromotionalGiftController {

    private final PromotionalGiftInterface iGift;

    @GetMapping("/all")
    @Operation(summary = "Find All PromotionalGift",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllListGift() {
        List<PromotionalGift> list = iGift.getAllGift();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a PromotionalGift with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "PromotionalGift not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getGiftId(@PathVariable("id") Integer id) {
        PromotionalGift gift = iGift.getByGiftId(id);
        if (gift != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", gift));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " PromotionalGift dose not exist"));

    }

    @Operation(summary = "Find a PromotionalGift with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "PromotionalGift not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getByGiftName(@PathVariable("name") String giftName) {

        PromotionalGift gift = iGift.findByPromotionalGiftName(giftName).orElse(null);
        if (gift != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", gift));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "PromotionalGift dose not exist"));
    }
}
