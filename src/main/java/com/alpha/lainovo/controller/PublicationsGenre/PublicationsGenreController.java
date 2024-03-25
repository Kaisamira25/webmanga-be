package com.alpha.lainovo.controller.PublicationsGenre;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.GenreService;
import com.alpha.lainovo.service.PublicationsGenreService;
import com.alpha.lainovo.service.PublicationsService;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/v1/publications_genre")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsGenreController {

    private final PublicationsGenreService publicationsGenreService;
    private final PublicationsInterface iPublications;
    private final GenreService genreSer;

    @Operation(summary = "Add a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}")
    public ResponseEntity<Message> addGenreToPublications(@PathVariable Integer publicationsId, @RequestBody List<Integer> genres) {
        for(Integer genre:genres){
            publicationsGenreService.addGenreToPublications(publicationsId,genre);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}")
    public ResponseEntity<Message> removeGenreFromPublications(@PathVariable Integer publicationsId) {
        boolean status = publicationsGenreService.removeGenreFromPublications(publicationsId);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the Publication"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove genre, Genre does not exist in the Publication"));
        }
    }
    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @GetMapping("/{publicationsId}")
    public ResponseEntity<Message> getAllGenreByID(@PathVariable Integer publicationsId) {
        Set<Genre> genres = genreSer.getGenresByPublicationId(publicationsId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Successfully",genres ));

    }


}

