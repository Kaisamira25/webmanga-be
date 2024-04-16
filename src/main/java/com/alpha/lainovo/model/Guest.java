package com.alpha.lainovo.model;

import com.alpha.lainovo.model.enums.CustomerRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Integer customerId;

    @Column(name = "phong_number",columnDefinition = "nvarchar(10)",nullable = false)
    private String phoneNumber;

    @Column(name = "customer_email",columnDefinition = "nvarchar(100)",nullable = false)
    private String email;

    @Column(name = "customer_fullname",columnDefinition = "nvarchar(50)",nullable = false)
    private String fullName;

    @Column(name = "addresses",columnDefinition = "nvarchar(500)",nullable = false)
    private String addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "guest")
    private Set<Orders> ordersList=new HashSet<>();
}
