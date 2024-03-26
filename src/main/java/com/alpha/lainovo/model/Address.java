package com.alpha.lainovo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;
    @Column(name = "district",columnDefinition = "nvarchar(100)",nullable = false)
    private String district;
    @Column(name = "city",columnDefinition = "nvarchar(100)",nullable = false)
    private String city;
    @Column(name = "phone_number",columnDefinition = "varchar(20)",nullable = false)
    private String phoneNumber;
    @Column(name = "ward",columnDefinition = "nvarchar(100)",nullable = false)
    private String ward;
}
