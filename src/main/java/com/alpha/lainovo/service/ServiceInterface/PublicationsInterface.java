package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.PublicationsDetailsDTO;
import com.alpha.lainovo.dto.request.PublicationsHotPublicationsDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.PublicationsNewArrivalDTO;
import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface PublicationsInterface extends ICreateAndUpdateV2<Integer, Publications>{
    boolean delete(Integer id);
    Optional<Publications> findByName(String name);
    Page<Publications> getAllPagePublicationsWithImage(int page, int size, String sortField, String sortBy, Integer genre);
    List<PublicationsHotPublicationsDTO> getBestSellerPublicationsWithImage();
    List<PublicationsNewArrivalDTO> getNewArrivalPublicationsWithImage();
    Publications getByPublicationsId(Integer id);
    List<Publications> getAllPublications();
    List<Publications> getPublicationsbyName(String name);
    List<Publications> getPublicationsByAuthorName(String authorName);
    Publications getPublicationsDetailsById(Integer id);
}
