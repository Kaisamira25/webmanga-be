package com.alpha.lainovo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Genre")
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer genreID;

    @Column(name = "genre", columnDefinition = "nvarchar(100)", nullable = false)
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private Set<Publications> publications = new HashSet<>();
}
