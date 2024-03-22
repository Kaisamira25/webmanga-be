package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface PublicationsInterface extends ICreateAndUpdateV2<Integer, Publications>{

    boolean delete(Integer id);

    Optional<Publications> findByName(String name);

    Page<Publications> getAllPagePublications(int page, int size, String sortField, String sortBy);
    List<Publications> getBestSellerPublications();
    List<Publications> getNewArrivalPublications();

    Publications getByPublicationsId(Integer id);
    List<Publications> getAllPublications();


}
