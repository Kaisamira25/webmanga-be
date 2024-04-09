package com.alpha.lainovo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicationGenreDTO {
    private Integer publication_id;
    private Integer genre_id;
}
