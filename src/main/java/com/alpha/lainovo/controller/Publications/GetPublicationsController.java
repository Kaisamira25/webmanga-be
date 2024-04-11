package com.alpha.lainovo.controller.Publications;

import com.alpha.lainovo.dto.request.PublicationsDetailsDTO;
import com.alpha.lainovo.dto.request.PublicationsHotPublicationsDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.PublicationsNewArrivalDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.service.PublicationsService;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
@Slf4j
public class GetPublicationsController {

    private final PublicationsInterface PublicationsInterface;
    private final PublicationsService publicationsService;

    @GetMapping("/all")
    @Operation(summary = "Find All Publications",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getAllPublications() {
        List<Publications> publications = publicationsService.getAllPublications();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", publications));
    }

    @GetMapping("/details")
    @Operation(summary = "Find All Publications",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getAllPublicationsWithImage() {
        List<PublicationsImageDTO> publications = publicationsService.getAllPublicationsWithImage();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", publications));
    }

    @GetMapping("/id/{publicationsDetailsId}")
    @Operation(summary = "Find publications", description = "Find publications details with id",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "fail", responseCode = "400")})
    public ResponseEntity<?> getPublicationsDetailsWithId(@PathVariable("publicationsDetailsId") Integer id) {
        Publications publications = PublicationsInterface.getPublicationsDetailsById(id);
        PublicationsDetailsDTO publicationsDetailsDTO = publicationsService.publicationsToPublicationDetailsDTO(publications);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successful",publicationsDetailsDTO));
    }


    @GetMapping("/{publicationsId}")
    @Operation(summary = "Find a Publications with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getPublicationsId(@PathVariable("publicationsId") Integer id) {
        Publications publications = PublicationsInterface.getByPublicationsId(id);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", publications));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Publications dose not exist"));

    }

    @Operation(summary = "Find a Publications with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<?> getByPublicationsTitle(@PathVariable("name") String name) {

        List<Publications> publications = PublicationsInterface.getPublicationsbyName(name);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", publications));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Publications dose not exist"));
    }

    @GetMapping("/pagination")
    @Operation(summary = "get publications", description = "Get All Pagination Publications with Images", responses = {
            @ApiResponse(description = "success", responseCode = "200"), })
    public ResponseEntity<?> getAllPagePublicationsWithImage(
            @RequestParam(defaultValue = "0", value = "page", required = false) int page,
            @RequestParam(defaultValue = "18", value = "size", required = false) int size,
            @RequestParam(defaultValue = "arrivalDay", value = "sortField", required = false) String sortField,
            @RequestParam(defaultValue = "desc", value = "sortBy", required = false) String sortBy,
            @RequestParam(required = false) Integer genreId) {
        Page<Publications> pagePublications = publicationsService.getAllPagePublicationsWithImage(page, size, sortField, sortBy, genreId);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", pagePublications));
    }
    @GetMapping("/best-sellers")
    @Operation(summary = "Get TOP 4 Best Selling Publications with Images",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getBestSellerPublicationsWithImage() {
        List<PublicationsHotPublicationsDTO> bestSellers = publicationsService.getBestSellerPublicationsWithImage();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", bestSellers));
    }

    @GetMapping("/new-arrivals")
    @Operation(summary = "Get TOP 4 New Publications with Images",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getNewArrivalPublicationsWithImage() {
        List<PublicationsNewArrivalDTO> newArrivals = publicationsService.getNewArrivalPublicationsWithImage();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", newArrivals));
    }

}
