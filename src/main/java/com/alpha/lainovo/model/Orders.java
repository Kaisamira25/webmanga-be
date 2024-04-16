package com.alpha.lainovo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderID;

    @Column(name="total_price")
    private Double totalPrice;

    @Column( columnDefinition = "varchar(10)" ,name = "phone_number")
    private String phoneNumber;

    @Column( columnDefinition = "varchar(25)" ,name = "fullname")
    private String fullname;

    @Column( columnDefinition = "varchar(255)" ,name = "address")
    private String address;

    @Column( columnDefinition = "bit(1)" ,name = "payment_status")
    private boolean paymentStatus;

    @Column( columnDefinition = "nvarchar(100)" ,name = "order_status")
    private String orderStatus;

    @Column(name="order_day")
    private Date orderDay;

    @ManyToOne
    @JoinColumn(name="guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL )
    private List<OrderItem> orderItem;

    @ManyToOne
    @JoinColumn(name="discount_id",nullable = true)
    private Discount discount;
}
