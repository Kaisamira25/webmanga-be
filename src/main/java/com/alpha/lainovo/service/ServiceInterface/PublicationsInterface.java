package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface PublicationsInterface extends ICreateAndUpdateV2<Integer, Publications>{

    boolean delete(Integer id);

    Optional<Publications> findByName(String name);

    Page<PublicationsImageDTO> getAllPagePublicationsWithImage(int page, int size, String sortField, String sortBy);
    List<PublicationsImageDTO> getBestSellerPublicationsWithImage();
    List<PublicationsImageDTO> getNewArrivalPublicationsWithImage();

    Publications getByPublicationsId(Integer id);
    List<Publications> getAllPublications();

    List<Publications> getPublicationsbyName(String name);
}
