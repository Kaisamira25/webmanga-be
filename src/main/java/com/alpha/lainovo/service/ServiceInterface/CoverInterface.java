package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;


import java.util.List;
import java.util.Optional;

public interface CoverInterface extends ICreateAndUpdateV2<Integer, Cover> {

    boolean delete(Integer id);
    Cover getByCoverId(Integer id);
    Optional<Cover> findByCoverType(String coverType);
    List<Cover> getAllCover();

    List<Cover> getCoverListbyCover(String cover);
}
