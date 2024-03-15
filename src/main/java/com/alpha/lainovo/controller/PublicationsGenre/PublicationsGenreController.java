package com.alpha.lainovo.controller.PublicationsGenre;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.PublicationsGenreService;
import com.alpha.lainovo.service.PublicationsService;
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
@RequestMapping("/api/v1/publications_genre")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsGenreController {

    private final PublicationsGenreService publicationsGenreService;

    @Operation(summary = "Add a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}/genres/{genreId}")
    public ResponseEntity<Message> addGenreToPublications(@PathVariable Integer publicationsId, @PathVariable Integer genreId) {
        publicationsGenreService.addGenreToPublications(publicationsId, genreId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}/genres/{genreId}")
    public ResponseEntity<Message> removeGenreFromPublications(@PathVariable Integer publicationsId, @PathVariable Integer genreId) {
        boolean status = publicationsGenreService.removeGenreFromPublications(publicationsId, genreId);
        if (status) {
            log.info(">>>>>> PublicationsController:removeGenreFromPublications | Successfully removed genre with id: {} from Publications with id: {}", genreId, publicationsId);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the Publication"));
        } else {
            log.error(">>>>>>> PublicationsController:removeGenreFromPublications | Failed to remove genre with id: {} from Publications with id: {}. Genre not found in the publication's genres.", genreId, publicationsId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove genre, Genre does not exist in the Publication"));
        }
    }


}

