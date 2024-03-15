package com.alpha.lainovo.controller.Publications;

import com.alpha.lainovo.dto.request.PublicationsDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.PublicationsService;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsController {

    private final PublicationsInterface iPublications;

    private final ModelMapper modelMapper;

    private final PublicationsService publicationsService;

    @Operation(summary = "Create a publications", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @PostMapping()
    public ResponseEntity<Message> createPublications(@RequestBody PublicationsDTO publicationsDTO) {
        Publications publications = modelMapper.map(publicationsDTO, Publications.class);
        publications = (Publications) iPublications.create(publications);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", publications));
    }

    @Operation(summary = "Update a Publications", description = "When the Publications is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "400")})
    @PutMapping("/{id}")
    public ResponseEntity<Message> updatePublications(@PathVariable("id") Integer id, @RequestBody PublicationsDTO mangaDTO) {
        Publications publications = modelMapper.map(mangaDTO, Publications.class);
        publications = iPublications.update(id, publications);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "updated successfully", publications));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, MangaAndLightNovels dose not exist"));

    }

    @Operation(summary = "Delete a Publications", description = "When the Publications is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "400")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deletePublications(@PathVariable("id") Integer id) {

        boolean status = iPublications.delete(id);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "deleted successfully"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "deleted fail, Publications dose not exist"));

    }

}
