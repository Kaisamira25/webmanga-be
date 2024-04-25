package com.alpha.lainovo.controller.Discount;

import com.alpha.lainovo.dto.request.DiscountDTO;
import com.alpha.lainovo.dto.request.GenreDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.service.DiscountService;
import com.alpha.lainovo.service.ServiceInterface.DiscountInterface;
import com.alpha.lainovo.service.ServiceInterface.GenreInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DiscountController {

    private final DiscountInterface iDiscount;
    private final DiscountService discountService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Create a Discount", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @SecurityRequirement(name="bearerAuth")
    @PostMapping()
    public ResponseEntity<Message> createDiscounts(@RequestBody DiscountDTO discountDTO) {
        Discount discounts = modelMapper.map(discountDTO, Discount.class);
        discounts.setCreatedAt(new Date());
        discounts.setActive(true);
        discounts = (Discount) iDiscount.create(discounts);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", discounts));
    }

    @Operation(summary = "Update a Discount", description = "When the Discount is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Discount not found", responseCode = "400")})
    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateDiscount(@PathVariable("id") Integer id, @RequestBody DiscountDTO discountDTODTO) {
        Discount discounts = modelMapper.map(discountDTODTO, Discount.class);
        if(discounts.getExpirationDate().before(new Date()) ) {
            discounts.setActive(false);
        }else{
            discounts.setActive(true);
        }
        discounts = iDiscount.update(id, discounts);
        if (discounts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Discount updated successfully", discounts));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, Discount dose not exist"));

    }

    @Operation(summary = "Delete a Discount", description = "When the Discount is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Discount not found", responseCode = "400")})
    @SecurityRequirement(name="bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteDiscount(@PathVariable("id") Integer id) {
        boolean status = iDiscount.delete(id);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Discount deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "deleted fail, Discount dose not exist"));

    }

    @Operation(summary = "Check and deactivate expired discounts", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/check-expired")
    public ResponseEntity<Message> checkAndDeactivateExpiredDiscounts() {
        discountService.deactivateExpiredDiscounts(); // Thêm dòng này
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Checked and deactivated expired discounts"));
    }
}
