package com.alpha.lainovo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationsDTO {
    private String publicationsName;
    private Float unitPrice;
    private Integer stock;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String summary;
    private Date arrivalDay;
}
