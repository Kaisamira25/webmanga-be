package com.alpha.lainovo.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicationsDTO {
    private String publicationsName;
    private Double unitPrice;
    private Integer stock;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String summary;
    private Date arrivalDay;
}
