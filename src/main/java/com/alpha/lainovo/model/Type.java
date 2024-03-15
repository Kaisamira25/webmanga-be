package com.alpha.lainovo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Type")
public class Type implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer TypeID;

    @Column(name = "type_name", columnDefinition = "nvarchar(100)", nullable = false)
    private String typeName;

    @ManyToMany(mappedBy = "types")
    private Set<Publications> publications = new HashSet<>();

}
