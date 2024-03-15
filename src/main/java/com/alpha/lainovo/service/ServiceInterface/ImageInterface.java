package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.RImageDTO;
import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.PromotionalGift;

import java.util.Optional;

public interface ImageInterface{

    Image findById (Integer id);
    Image saveImage(Integer id, RImageDTO imageDTO);
    boolean deleteImage(Integer id);

}
