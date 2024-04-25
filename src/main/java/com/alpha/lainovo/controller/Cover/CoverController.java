package com.alpha.lainovo.controller.Cover;


import com.alpha.lainovo.dto.request.CoverDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.service.ServiceInterface.CoverInterface;
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
@RequestMapping("/api/v1/cover")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CoverController {

    private final CoverInterface iCover;

    private final ModelMapper modelMapper;

    @Operation(summary = "Create a Cover", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @SecurityRequirement(name="bearerAuth")
    @PostMapping()
    public ResponseEntity<Message> createCover(@RequestBody CoverDTO coverDTO) {
        Cover cover = modelMapper.map(coverDTO, Cover.class);
        cover = (Cover) iCover.create(cover);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", cover));
    }

    @Operation(summary = "Update a Cover", description = "When the Cover is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Cover not found", responseCode = "400")})
    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCover(@PathVariable("id") Integer id, @RequestBody CoverDTO coverDTO) {
        Cover cover = modelMapper.map(coverDTO, Cover.class);
        cover = iCover.update(id, cover);
        if (cover != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "updated successfully", cover));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, CoverDTO dose not exist"));

    }

    @Operation(summary = "Delete a Cover", description = "When the Cover is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Cover not found", responseCode = "400")})
    @SecurityRequirement(name="bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteCover(@PathVariable("id") Integer id) {
        boolean status = iCover.delete(id);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "deleted fail, Cover dose not exist"));

    }
}
