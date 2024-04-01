package com.alpha.lainovo.controller.Publications;

import com.alpha.lainovo.dto.request.PublicationsImageDTO;
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
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
@Slf4j
public class GetPublicationsController {

    private final PublicationsInterface iPublications;
    private final PublicationsService publicationsService;

    @GetMapping("/all")
    @Operation(summary = "Find All Publications",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<?> getAllPublications() {
        List<Publications> publications = publicationsService.getAllPublications();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", publications));
    }


    @GetMapping("/{publicationsId}")
    @Operation(summary = "Find a Publications with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getPublicationsId(@PathVariable("publicationsId") Integer id) {
        Publications publications = iPublications.getByPublicationsId(id);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", publications));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Publications dose not exist"));

    }

    @Operation(summary = "Find a Publications with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Publications not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getByPublicationsTitle(@PathVariable("name") String name) {

        List<Publications> publications = iPublications.getPublicationsbyName(name);
        if (publications != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", publications));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Publications dose not exist"));
    }

    @GetMapping("/pagination")
    @Operation(summary = "get publications", description = "Get All Pagination Publications with Images", responses = {
            @ApiResponse(description = "success", responseCode = "200"), })
    public ResponseEntity<Message> getAllPagePublicationsWithImage(
            @RequestParam(defaultValue = "0", value = "page", required = false) int page,
            @RequestParam(defaultValue = "9", value = "size", required = false) int size,
            @RequestParam(defaultValue = "arrivalDay", value = "sortField", required = false) String sortField,
            @RequestParam(defaultValue = "desc", value = "sortBy", required = false) String sortBy) {

        Page<Publications> pagePublications = publicationsService.getAllPagePublicationsWithImage(page, size, sortField, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", pagePublications));
    }

    @GetMapping("/best-sellers")
    @Operation(summary = "Get TOP 4 Best Selling Publications with Images",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<Message> getBestSellerPublicationsWithImage() {
        List<PublicationsImageDTO> bestSellers = publicationsService.getBestSellerPublicationsWithImage();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", bestSellers));
    }

    @GetMapping("/new-arrivals")
    @Operation(summary = "Get TOP 4 New Publications with Images",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<Message> getNewArrivalPublicationsWithImage() {
        List<PublicationsImageDTO> newArrivals = publicationsService.getNewArrivalPublicationsWithImage();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", newArrivals));
    }

}
