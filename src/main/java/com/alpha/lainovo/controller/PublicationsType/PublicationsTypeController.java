package com.alpha.lainovo.controller.PublicationsType;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.service.PublicationsGenreService;
import com.alpha.lainovo.service.PublicationsTypeService;
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
@RequestMapping("/api/v1/publications_type")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsTypeController {

    private final PublicationsTypeService publicationsTypeService;

    @Operation(summary = "Add a Type from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}/types/{typeId}")
    public ResponseEntity<Message> addTypeToPublications(@PathVariable Integer publicationsId, @PathVariable Integer typeId) {
        publicationsTypeService.addTypeToPublications(publicationsId, typeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "Successful", null));
    }

    @Operation(summary = "Remove a Type from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Type not found", responseCode = "400")})
    @DeleteMapping("/{publicationsId}/types/{typeId}")
    public ResponseEntity<Message> removeGenreFromPublications(@PathVariable Integer publicationsId, @PathVariable Integer typeId) {
        boolean status = publicationsTypeService.removeTypeFromPublications(publicationsId, typeId);
        if (status) {
            log.info(">>>>>> PublicationsTypeController:removeTypeFromPublications | Successfully removed Type with id: {} from Publications with id: {}", typeId, publicationsId);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Genre removed successfully from the publication"));
        } else {
            log.error(">>>>>>> PublicationsTypeController:removeTypeFromPublications | Failed to remove Type with id: {} from Publications with id: {}. Genre not found in the publication's genres.", typeId, publicationsId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Failed to remove Type, Type does not exist in the Publication"));
        }
    }


}
