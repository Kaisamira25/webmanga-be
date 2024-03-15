package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Cover;


import java.util.List;
import java.util.Optional;

public interface CoverInterface extends CreateAndUpdateInterface<Integer, Cover> {

    boolean delete(Integer id);
    Cover getByCoverId(Integer id);
    Optional<Cover> findByCoverType(String coverType);
    List<Cover> getAllCover();
}
