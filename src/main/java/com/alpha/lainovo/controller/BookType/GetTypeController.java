package com.alpha.lainovo.controller.BookType;

import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.service.ServiceInterface.TypeInterface;
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
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
public class GetTypeController {

    private final TypeInterface iType;

    @GetMapping("/all")
    @Operation(summary = "Find All Type",responses = {
            @ApiResponse(description = "success", responseCode = "200")})
    public ResponseEntity<Message> getAllListType() {
        List<Type> list = iType.getAllType();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Type with the ID",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Type not found", responseCode = "404")})
    public ResponseEntity<Message> getByTypeId(@PathVariable("id") Integer id) {
        Type type = iType.getByTypeId(id);
        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Successfully", type));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, " Type dose not exist"));

    }

    @Operation(summary = "Find a Type with the given name.",responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Type not found", responseCode = "404")})
    @GetMapping("/search/{name}")
    public ResponseEntity<Message> getType(@PathVariable("name") String typeName) {

        List<Type> type = iType.getTypeListbyType(typeName);
        if (type != null && !type.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, " successfully", type));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Type dose not exist"));
    }
}
