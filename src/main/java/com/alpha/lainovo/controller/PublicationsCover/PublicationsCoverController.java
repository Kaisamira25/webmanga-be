package com.alpha.lainovo.controller.PublicationsCover;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.service.CoverService;
import com.alpha.lainovo.service.GenreService;
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

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/v1/publications_cover")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsCoverController {

    private final PublicationsCoverService publicationsCoverService;
    private final CoverService coverSer;

    @Operation(summary = "Add a Cover from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Message> addCoverToPublications(@PathVariable Integer publicationsId,  @RequestBody List<Integer> covers) {
        for(Integer cover:covers){
            publicationsCoverService.addCoverToPublications(publicationsId,cover);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Cover from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Cover not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Message> removeCoverFromPublications(@PathVariable Integer publicationsId) {
        boolean status = publicationsCoverService.removeCoverFromPublications(publicationsId);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the publication"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove Cover, Cover does not exist in the Publication"));
        }
    }
    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @GetMapping("/{publicationsId}")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Message> getAllCoverByID(@PathVariable Integer publicationsId) {
        Set<Cover> covers = coverSer.getCoverByPublicationId(publicationsId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Successfully",covers ));

    }

}

