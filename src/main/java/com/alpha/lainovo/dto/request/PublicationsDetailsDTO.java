package com.alpha.lainovo.dto.request;

import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Type;

import java.util.List;

public record PublicationsDetailsDTO(
        Integer publicationsId,
        String publicationsName,
        Double unitPrice,
        Integer stock,
        String author,
        String publisher,
        Integer publicationYear,
        String summary,
        String imageURL,
        List<String> cover,
        List<String> genres,
        List<String> types
) {
}
