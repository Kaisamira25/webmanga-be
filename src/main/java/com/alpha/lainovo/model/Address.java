package com.alpha.lainovo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Address")
public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addressID")
	private Integer addressID;

	@Column(name = "phone_number",columnDefinition = "nvarchar(15)",nullable = false,unique = true)
	private String phoneNumber;


	@Column(name = "city", columnDefinition = "nvarchar(100)")
	private String city;

	@Column(name = "district",columnDefinition = "nvarchar(100)")
	private String district;

	@Column(name = "ward", columnDefinition = "nvarchar(100)")
	private String ward;;

	@Column(name = "address",columnDefinition = "nvarchar(100)")
	private String address;

	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
