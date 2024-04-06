package com.alpha.lainovo.controller.CustomerAddress;

import com.alpha.lainovo.dto.request.RCustomerAddressDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.model.Address;
import com.alpha.lainovo.service.ServiceInterface.AddressInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/address")
@SecurityRequirement(name = "bearerAuth")
public class CustomerAddressController {

    private  final AddressInterface addressInterface;

    @Operation(summary = "Get Address By Customer", description = "Get Address By Customer", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Customer not found", responseCode = "404")})
    @GetMapping()
    public ResponseEntity<Message> getAddressByCustomer(HttpServletRequest request) {
        Address address = addressInterface.getAddressByCustomer(request);
        if (address != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Get address success", address));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(0, "Customer not found", null));
    }


    @Operation(summary = "Add a Address By Customer", description = "Add a Address By Customer", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Add address failed, customer not found", responseCode = "400")})
    @PostMapping()
    public ResponseEntity<Message> addAddress(@RequestBody RCustomerAddressDTO addressDTO, HttpServletRequest request) {

        Address newAddress = addressInterface.addAddress(addressDTO, request);
        if (newAddress != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Add address success", newAddress));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Add address fail", null));
    }


    @Operation(summary = "Update a Address By Customer", description = "Update a Address By Customer", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Update not found", responseCode = "404")})
    @PutMapping()
    public ResponseEntity<Message> updateAddress(@RequestBody RCustomerAddressDTO addressDTO, HttpServletRequest request) {

        Address address = addressInterface.updateAddress(addressDTO,request);
        if (address != null) {
            if (addressDTO.phoneNumber() != null && !addressDTO.phoneNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Update phone number success",address));
            }
            if (addressDTO.address() != null && !addressDTO.address().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new Message(1, "Update address success",address));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(0, "Update address fail",address));
    }

}
