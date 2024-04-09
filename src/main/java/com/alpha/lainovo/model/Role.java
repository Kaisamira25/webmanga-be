package com.alpha.lainovo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name = "role_name",columnDefinition = "varchar(50)",nullable = false)
    private String roleName;

//    @ManyToOne
//    private Admin admin;
@ManyToMany(mappedBy = "roles")
private List<Admin> admins;


    public Role(String role) {
        this.roleName = role;
    }
}
