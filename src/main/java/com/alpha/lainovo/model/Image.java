package com.alpha.lainovo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageID;

    @Column(name = "image_url", columnDefinition = "nvarchar(255)")
    private String imageURL;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "publications_id")
    private Publications publications;
}
