package com.alpha.lainovo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userid;
    @Column(name = "user_email",unique = true,columnDefinition = "nvarchar(100)",nullable = false)
    private String email;
    @Column(name = "user_fullname",columnDefinition = "nvarchar(255)",nullable = false)
    private String fullName;
    @JsonIgnore
    @Column(name = "user_password",columnDefinition = "varchar(100)",nullable = false)
    private String password;
    @JsonIgnore
    @Column(name = "user_token",columnDefinition = "nvarchar(1000)")
    private String userToken;
    @JsonIgnore
    @Column(name = "user_verify_code",columnDefinition = "varchar(255)", nullable = false)
    private String userVerifyCode;
    @JsonIgnore
    @Column(name = "user_expiration_date")
    private Date userVerifyCodeExpirationTime;
    @Column(name = "user_verify")
    private Boolean isVerify = false;
    @Column(name = "user_create_time")
    private Date createdAt;
    @JsonIgnore
    @Column(name = "user_reset_password",columnDefinition = "varchar(100)")
    private String resetPasswordCode;
    @JsonIgnore
    @Column(name = "user_reset_password_expiration")
    private Date resetPasswordExpirationTime;
    @ManyToMany(mappedBy = "users") // mappedBy = "tên biến ở class đang liên kết"
    private Set<Role> roles; // dùng set vì set sẽ có chức năng tránh trùng lặp các giá trị trong cùng một mảng
}
