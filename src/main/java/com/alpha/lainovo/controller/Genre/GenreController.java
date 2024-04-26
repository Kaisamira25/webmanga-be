package com.alpha.lainovo.controller.Genre;

import com.alpha.lainovo.dto.request.GenreDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.service.ServiceInterface.GenreInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GenreController {

    private final GenreInterface iGenre;

    private final ModelMapper modelMapper;

    @Operation(summary = "Create a Genre", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})

    @PostMapping("/create")
    public ResponseEntity<Message> createGenres(@RequestBody GenreDTO genresDTO) {
        Genre genres = modelMapper.map(genresDTO, Genre.class);
        genres = (Genre) iGenre.create(genres);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", genres));
    }

    @Operation(summary = "Update a Genre", description = "When the Genre is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Genre not found", responseCode = "400")})

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateGenre(@PathVariable("id") Integer id, @RequestBody GenreDTO genresDTO) {
        Genre genres = modelMapper.map(genresDTO, Genre.class);
        genres = iGenre.update(id, genres);
        if (genres != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "updated successfully", genres));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, genre dose not exist"));

    }

    @Operation(summary = "Delete a Genre", description = "When the Genre is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Genre not found", responseCode = "400")})

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteGenre(@PathVariable("id") Integer id) {

        boolean status = iGenre.delete(id);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "deleted successfully"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "deleted fail, Genre dose not exist"));

    }
}
