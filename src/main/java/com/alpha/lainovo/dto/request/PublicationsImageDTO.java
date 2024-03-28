package com.alpha.lainovo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicationsImageDTO {
    private Integer publicationsId;
    private String publicationsName;
    private Float unitPrice;
    private Integer stock;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String summary;
    private Date arrivalDay;
    private String imageURL;
}
