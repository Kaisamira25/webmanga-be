package com.alpha.lainovo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Publications")
public class Publications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publications_id")
    private Integer publicationsID;

    @Column(name = "publications_name", columnDefinition = "nvarchar(300)", nullable = false)
    private String publicationsName;

    @Column(name = "unit_price", columnDefinition = "float",  nullable = false)
    private Double unitPrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "author", columnDefinition = "nvarchar(255)", nullable = false)
    private String author;

    @Column(name = "publisher", columnDefinition = "nvarchar(255)", nullable = false)
    private String publisher;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(name = "summary", columnDefinition = "nvarchar(1000)", nullable = false)
    private String summary;

    @Column(name = "arrival_day",nullable = false)
    private Date arrivalDay;


    @OneToMany(mappedBy = "publications", cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Publications_Genre",
            joinColumns = @JoinColumn(name = "publications_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Publications_Cover",
            joinColumns = @JoinColumn(name = "publications_id"),
            inverseJoinColumns = @JoinColumn(name = "cover_id")
    )
    private Set<Cover> covers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Publications_Type",
            joinColumns = @JoinColumn(name = "publications_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<Type> types = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Publications_gift",
            joinColumns = @JoinColumn(name = "publications_id"),
            inverseJoinColumns = @JoinColumn(name = "promotional_gift_id")
    )
    private Set<PromotionalGift> gifts = new HashSet<>();

    @OneToMany(mappedBy = "publications")
    private List<OrderItem> orderItem;

}
