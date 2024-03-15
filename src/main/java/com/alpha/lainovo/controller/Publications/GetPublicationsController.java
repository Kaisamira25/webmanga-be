package com.alpha.lainovo.controller.Publications;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.PublicationsService;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class GetPublicationsController {

    private final PublicationsInterface iPublications;
    private final PublicationsService publicationsService;

    @GetMapping("/all")
    @Operation(summary = "Find All Publications",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllListPublications() {
        List<Publications> list = iPublications.getAllPublications();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Publications with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getPublicationsId(@PathVariable("id") Integer id) {
        Publications manga = iPublications.getByPublicationsId(id);
        if (manga != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", manga));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Publications dose not exist"));

    }

    @Operation(summary = "Find a Publications with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getByPublicationsTitle(@PathVariable("name") String name) {

        Publications publications = iPublications.findByName(name).orElse(null);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", publications));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Publications dose not exist"));
    }

//    @Operation(summary = "Find All PublicationsGenres.",responses = {
//            @ApiResponse(description = "success", responseCode = "200"),
//            @ApiResponse(description = "PublicationsGenres not found", responseCode = "404")})
//    @GetMapping("/genres")
//    public ResponseEntity<List<PublicationsGenreDTO>> getAllPublicationsGenres() {
//        List<PublicationsGenreDTO> list = publicationsService.getAllPublicationsGenres();
//        return ResponseEntity.ok(list);
//    }
//
//    @Operation(summary = "Find all Publications that belong to a Genre .",responses = {
//            @ApiResponse(description = "success", responseCode = "200"),
//            @ApiResponse(description = "Genre not found", responseCode = "404")})
//    @GetMapping("/genres")
//    public ResponseEntity<?> getPublicationsByGenre(@RequestBody Map<String, Integer> body) {
//        Integer genreId = body.get("genreId");
//        try {
//            List<Publications> list = publicationsService.getPublicationsByGenre(genreId);
//            return ResponseEntity.ok(list);
//        } catch (ResponseStatusException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "failed: genre not found", null));
//        }
//    }
}
