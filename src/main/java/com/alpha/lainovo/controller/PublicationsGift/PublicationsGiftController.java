package com.alpha.lainovo.controller.PublicationsGift;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.service.PromotionalGiftService;
import com.alpha.lainovo.service.PublicationsGenreService;
import com.alpha.lainovo.service.PublicationsGiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/v1/publications_gift")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsGiftController {

    private final PublicationsGiftService publicationsGiftService;
    private final PromotionalGiftService giftSer;

    @Operation(summary = "Add a Gift from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}")
    public ResponseEntity<Message> addGiftToPublications(@PathVariable Integer publicationsId, @RequestBody List<Integer> gifts) {
        for(Integer gift:gifts){
            publicationsGiftService.addGiftToPublications(publicationsId,gift);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Gift from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Gift not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}")
    public ResponseEntity<Message> removeGiftFromPublications(@PathVariable Integer publicationsId) {
        boolean status = publicationsGiftService.removeGiftFromPublications(publicationsId);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the Publication"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove Gift, Gift does not exist in the publication"));
        }
    }
    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @GetMapping("/{publicationsId}")
    public ResponseEntity<Message> getAllGiftByID(@PathVariable Integer publicationsId) {
        Set<PromotionalGift> gifts = giftSer.getGiftByPublicationId(publicationsId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Successfully",gifts ));
    }

}

