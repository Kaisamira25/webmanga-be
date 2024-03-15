package com.alpha.lainovo.controller.PublicationsGift;

import com.alpha.lainovo.dto.response.Message;
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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/v1/publications_gift")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsGiftController {

    private final PublicationsGiftService publicationsGiftService;

    @Operation(summary = "Add a Gift from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}/gifts/{giftId}")
    public ResponseEntity<Message> addGiftToPublications(@PathVariable Integer publicationsId, @PathVariable Integer giftId) {
        publicationsGiftService.addGiftToPublications(publicationsId, giftId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Gift from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Gift not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}/gifts/{giftId}")
    public ResponseEntity<Message> removeGiftFromPublications(@PathVariable Integer publicationsId, @PathVariable Integer giftId) {
        boolean status = publicationsGiftService.removeGiftFromPublications(publicationsId, giftId);
        if (status) {
            log.info(">>>>>> PublicationsGiftController:removeGiftFromPublications | Successfully removed Gift with id: {} from Publications with id: {}", giftId, publicationsId);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the Publication"));
        } else {
            log.error(">>>>>>> PublicationsGiftController:removeGiftFromPublications | Failed to remove Gift with id: {} from Publications with id: {}. Gift not found in the Publication's Gift.", giftId, publicationsId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove Gift, Gift does not exist in the publication"));
        }
    }


}

