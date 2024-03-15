package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Publications;


import java.util.List;
import java.util.Optional;

public interface PublicationsInterface extends CreateAndUpdateInterface<Integer, Publications>{

    boolean delete(Integer id);

    Optional<Publications> findByName(String name);
    Publications getByPublicationsId(Integer id);
    List<Publications> getAllPublications();

//    Optional<Publications> findById(Integer id);

}
