package com.alpha.lainovo.dto.request;

import java.util.Date;

public record PublicationsNewArrivalDTO(
        Integer publicationsId,
        String publicationsName,
        Double unitPrice,
        Integer stock,
        String author,
        String publisher,
        Integer publicationsYear,
        String summary,
        Date arrivalDay,
        String imageURL
) {
}
