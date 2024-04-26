package com.alpha.lainovo.controller.BookType;

import com.alpha.lainovo.dto.request.TypeDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.service.ServiceInterface.TypeInterface;
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
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TypeController {

    private final TypeInterface iType;

    private final ModelMapper modelMapper;

    @Operation(summary = "Create a Type", responses = {
            @ApiResponse(description = "success", responseCode = "200"),})
    @PostMapping()

    public ResponseEntity<Message> createType(@RequestBody TypeDTO typeDTO) {
        Type type = modelMapper.map(typeDTO, Type.class);
        type = (Type) iType.create(type);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(1, "successful", type));
    }

    @Operation(summary = "Update a Type", description = "When the Type is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Type not found", responseCode = "400")})
    @PutMapping("/{id}")

    public ResponseEntity<Message> updateType(@PathVariable("id") Integer id, @RequestBody TypeDTO typeDTO) {
        Type type = modelMapper.map(typeDTO, Type.class);
        type = iType.update(id, type);
        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "updated successfully", type));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "updated fail, Type dose not exist"));

    }

    @Operation(summary = "Delete a Type", description = "When the Type is successfully updated, the response status code is 200; otherwise, it is 400, accompanied by a corresponding message.", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Type not found", responseCode = "400")})
    @DeleteMapping("/{id}")

    public ResponseEntity<Message> deleteType(@PathVariable("id") Integer id) {
        boolean status = iType.delete(id);
        if (status) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "deleted fail, Type dose not exist"));

    }
}
