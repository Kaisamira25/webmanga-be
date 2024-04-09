package com.alpha.lainovo.model;


import com.alpha.lainovo.model.enums.CustomerRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_email",unique = true,columnDefinition = "nvarchar(100)",nullable = false)
    private String email;

    @Column(name = "customer_fullname",columnDefinition = "nvarchar(255)",nullable = false)
    private String fullName;

    @JsonIgnore
    @Column(name = "customer_password",columnDefinition = "nvarchar(1000)",nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "customer_email_verify_code",columnDefinition = "varchar(20)")
    private String customerEmailVerifyCode;

    @JsonIgnore
    @Column(name = "customer_email_verify_code_expiration")
    private Date customerEmailVerifyCodeExpiration;

    @JsonIgnore
    @Column(name = "customer_reset_password_code",columnDefinition = "varchar(20)")
    private String customerResetPasswordCode;

    @JsonIgnore
    @Column(name = "customer_reset_password_code_expiration")
    private Date customerResetPasswordCodeExpiration;

    @Column(name = "customer_verify")
    private Boolean isVerify = false;

    @Column(name = "customer_blocked")
    private Boolean isBlocked = false;

    @Column(name = "customer_create_time")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "refresh_token",columnDefinition = "nvarchar(1000)")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",columnDefinition = "varchar(30)")
    private CustomerRole role = CustomerRole.CUSTOMER;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Address addresses;
}
