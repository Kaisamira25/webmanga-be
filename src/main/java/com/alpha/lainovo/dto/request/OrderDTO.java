package com.alpha.lainovo.dto.request;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String fullName;
    private Double totalPrice;
    private String phoneNumber;
    private String address;
    private Integer discount;
    private boolean paymentStatus;
    private String email;
    private List<OrderItemDTO> orderItem;
    private Boolean status;

}
