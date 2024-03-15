package com.alpha.lainovo.controller.PublicationsCover;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.PublicationsCoverService;
import com.alpha.lainovo.service.PublicationsGenreService;
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
@RequestMapping("/api/v1/publications_cover")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsCoverController {

    private final PublicationsCoverService publicationsCoverService;

    @Operation(summary = "Add a Cover from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}/covers/{coverId}")
    public ResponseEntity<Message> addCoverToPublications(@PathVariable Integer publicationsId, @PathVariable Integer coverId) {
        publicationsCoverService.addCoverToPublications(publicationsId, coverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Cover from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Cover not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}/covers/{coverId}")
    public ResponseEntity<Message> removeCoverFromPublications(@PathVariable Integer publicationsId, @PathVariable Integer coverId) {
        boolean status = publicationsCoverService.removeCoverFromPublications(publicationsId, coverId);
        if (status) {
            log.info(">>>>>> PublicationsCoverController:removeCoverFromPublications | Successfully removed Cover with id: {} from Publications with id: {}", coverId, publicationsId);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the publication"));
        } else {
            log.error(">>>>>>> PublicationsCoverController:removeCoverFromPublications | Failed to remove Cover with id: {} from Publications with id: {}. Cover not found in the publication's genres.", coverId, publicationsId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove Cover, Cover does not exist in the Publication"));
        }
    }


}

