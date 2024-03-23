package com.alpha.lainovo.controller.Genre;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Genre;
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
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GetGenreController {

    private final GenreInterface iGenre;

    @GetMapping("/all")
    @Operation(summary = "Find All Genre",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllListGenre() {
        List<Genre> list = iGenre.getAllGenre();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Genre with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Genre not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getGenreId(@PathVariable("id") Integer id) {
        Genre genres = iGenre.getByGenreId(id);
        if (genres != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", genres));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Genre dose not exist"));

    }

    @Operation(summary = "Find a Genre with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Genre not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getGenreByName(@PathVariable("name") String genresName) {
        List<Genre> list=iGenre.getGenreListbyGenre(genresName);
        System.out.println(list.size());
        if (list != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", list));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Genre dose not exist"));
    }
}
