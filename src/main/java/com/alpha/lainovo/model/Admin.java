package com.alpha.lainovo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(name = "account_name",nullable = false, unique = true)
    private String accountName;

    @Column(name = "full_name",columnDefinition = "nvarchar(255)",nullable = false)
    private String fullName;

    @Column(name = "password",columnDefinition = "varchar(1000)",nullable = false)
    private String password;

    @Column(name = "phone_number",columnDefinition = "varchar(12)")
    private String phone;

    @Column(name = "refresh_token",columnDefinition = "nvarchar(1000)")
    private String refreshToken;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "user_address",nullable = false)
    private String address;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "roleId")
    private Role role;

}
