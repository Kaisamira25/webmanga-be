package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.PromotionalGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionalGiftRepository extends JpaRepository<PromotionalGift, Integer> {
    Optional<PromotionalGift> findByPromotionalGiftName(String giftName);

    Optional<PromotionalGift> findByPromotionalGiftID(Integer id);

    List<PromotionalGift> findPromotionalGiftsByPromotionalGiftNameContaining(String string);

}
