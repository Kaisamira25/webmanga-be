package com.alpha.lainovo.controller.Discount;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.service.ServiceInterface.DiscountInterface;
import com.alpha.lainovo.service.ServiceInterface.GenreInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
public class GetDiscountController {

    private final DiscountInterface iDiscount;

    @GetMapping("/all")
    @Operation(summary = "Find All Discount",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllListGenre() {
        List<Discount> list = iDiscount.getAllDiscount();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Discount with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Discount not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getDiscountId(@PathVariable("id") Integer id) {
        Discount discounts = iDiscount.getByDiscountId(id);
        if (discounts != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", discounts));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Discount dose not exist"));

    }

    @Operation(summary = "Find a Discount with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Discount not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getDiscountByName(@PathVariable("name") String nameDiscount) {
        List<Discount> discounts=iDiscount.getDiscountListbyDiscountName(nameDiscount);
        if (discounts != null && !discounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", discounts));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Discount dose not exist"));
    }
}
