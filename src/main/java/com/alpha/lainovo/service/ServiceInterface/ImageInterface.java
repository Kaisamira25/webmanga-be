package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.RImageDTO;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.PromotionalGift;

import java.util.List;
import java.util.Optional;

public interface ImageInterface extends ICreateAndUpdateV2<Integer, Image>{

    Image findById (Integer id);
    List<Image> findAll();


}
