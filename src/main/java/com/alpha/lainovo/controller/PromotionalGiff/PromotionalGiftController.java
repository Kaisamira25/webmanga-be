package com.alpha.lainovo.controller.PromotionalGiff;

import com.alpha.lainovo.dto.request.PromotionalGiftDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.service.ServiceInterface.PromotionalGiftInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/gift")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PromotionalGiftController {

    private final PromotionalGiftInterface iGift;

    private final ModelMapper modelMapper;

    @Operation(summary = "PromotionalGift a Genres", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @PostMapping()
    public ResponseEntity<Message> createGift(@RequestBody PromotionalGiftDTO giftDTO) {
        PromotionalGift gift = modelMapper.map(giftDTO, PromotionalGift.class);
        gift = (PromotionalGift) iGift.create(gift);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", gift));
    }

    @Operation(summary = "Update a PromotionalGift", description = "When the PromotionalGift is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "PromotionalGift not found", responseCode = "400")})
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateGift(@PathVariable("id") Integer id, @RequestBody PromotionalGiftDTO giftDTO) {
        PromotionalGift gift = modelMapper.map(giftDTO, PromotionalGift.class);
        gift = iGift.update(id, gift);
        if (gift != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "updated successfully", gift));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, PromotionalGift dose not exist"));

    }

    @Operation(summary = "Delete a PromotionalGift", description = "When the PromotionalGift is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "PromotionalGift not found", responseCode = "400")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteGift(@RequestBody List<Integer> id) {
        for(Integer i:id){
            boolean status = iGift.delete(i);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "delete sucessfull"));

    }
}
