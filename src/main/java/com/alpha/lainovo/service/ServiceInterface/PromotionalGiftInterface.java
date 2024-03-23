package com.alpha.lainovo.service.ServiceInterface;



import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.PromotionalGift;

import java.util.List;
import java.util.Optional;

public interface PromotionalGiftInterface extends CreateAndUpdateInterface<Integer, PromotionalGift> {

    boolean delete(Integer id);

    Optional<PromotionalGift> findByPromotionalGiftName(String giftName);
    PromotionalGift getByGiftId(Integer id);
    List<PromotionalGift> getAllGift();
//    Optional<PromotionalGift> findById(Integer id);

    List<PromotionalGift> getGiftListbyNameAndType(String cover);
}
