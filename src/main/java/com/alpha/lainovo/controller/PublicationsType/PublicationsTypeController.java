package com.alpha.lainovo.controller.PublicationsType;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.service.PublicationsGenreService;
import com.alpha.lainovo.service.PublicationsTypeService;
import com.alpha.lainovo.service.TypeService;
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
@RequestMapping("/api/v1/publications_type")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PublicationsTypeController {

    private final PublicationsTypeService publicationsTypeService;

    private final TypeService typeSer;

    @Operation(summary = "Add a Type from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @PostMapping("/{publicationsId}")
    public ResponseEntity<Message> addTypeToPublications(@PathVariable Integer publicationsId, @RequestBody List<Integer> types) {
        for(Integer type:types){
            publicationsTypeService.addTypeToPublications(publicationsId,type);
        }
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
    @Operation(summary = "Remove a Genre from a Publication", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publication or Genre not found", responseCode = "400")})
    @GetMapping("/{publicationsId}")
    public ResponseEntity<Message> getAllTypeByID(@PathVariable Integer publicationsId) {
        Set<Type> types = typeSer.getTypesByPublicationId(publicationsId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Successfully",types ));
    }


}
