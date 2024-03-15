package com.alpha.lainovo.controller.Cover;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.service.ServiceInterface.CoverInterface;
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
@RequestMapping("/api/v1/cover")
@RequiredArgsConstructor
public class GetCoverController {

    private final CoverInterface iCover;

    @GetMapping("/all")
    @Operation(summary = "Find All Cover",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getAllListBookCover() {
        List<Cover> list = iCover.getAllCover();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Cover with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Cover not found", responseCode = "404")})
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Message> getByCoverId(@PathVariable("id") Integer id) {
        Cover cover = iCover.getByCoverId(id);
        if (cover != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", cover));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Cover dose not exist"));

    }

    @Operation(summary = "Find a Cover with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Cover not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getByCoverType(@PathVariable("name") String coverType) {

        Cover cover = iCover.findByCoverType(coverType).orElse(null);
        if (cover != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", cover));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Cover dose not exist"));
    }
}
