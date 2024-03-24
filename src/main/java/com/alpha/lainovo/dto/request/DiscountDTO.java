package com.alpha.lainovo.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private String discountName;
    private String description;
    private BigDecimal discountPercent;
    private Boolean active;
    private String discountCode;
    private Date createdAt;
    private Date expirationDate;
}
