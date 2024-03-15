package com.alpha.lainovo.service;

import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.repository.PromotionalGiftRepository;
import com.alpha.lainovo.service.ServiceInterface.PromotionalGiftInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionalGiftService implements PromotionalGiftInterface {
    @Override
    @Cacheable(cacheNames = "Genre", key = "'#id'")
    public PromotionalGift getByGiftId(Integer id) {
        return giftRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Gift", key = "'#giftList'")
    public List<PromotionalGift> getAllGift() {
        return giftRepo.findAll();
    }

    @Cacheable(cacheNames = "Gift", key = "'#gift'")
    @Override
    public Optional<PromotionalGift> findByPromotionalGiftName(String gift) {
        return giftRepo.findByPromotionalGiftName(gift);
    }


    private final PromotionalGiftRepository giftRepo;

    @Override
    public Object create(PromotionalGift giftDTO) {
        PromotionalGift gift = new PromotionalGift();
        gift.setPromotionalGiftName(giftDTO.getPromotionalGiftName());
        gift.setPromotionalGiftType(giftDTO.getPromotionalGiftType());
        giftRepo.save(gift);
        log.info(">>>>>> PromotionalGiftServiceImp:save | Create a PromotionalGift with name:{} ", gift.getPromotionalGiftName());
        return gift;
    }

    @Override
    @Cacheable(cacheNames = "Gift", key = "'#id'")
    public PromotionalGift update(Integer id, PromotionalGift giftDTO) {
        PromotionalGift gift = getByGiftId(id);
        if (gift != null) {
            gift.setPromotionalGiftName(giftDTO.getPromotionalGiftName());
            gift.setPromotionalGiftType(giftDTO.getPromotionalGiftType());
            giftRepo.save(gift);
            log.info(">>>>>> PromotionalGiftServiceImp:update | Update a PromotionalGift with name:{} ", gift.getPromotionalGiftName());
            return gift;
        }
        log.error(">>>>>>> PromotionalGiftServiceImp:update | No PromotionalGift found to update with id: {} ",id);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Gift", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        PromotionalGift gift = getByGiftId(id);
        if (gift != null) {
            giftRepo.delete(gift);
            return true;
        }
        return false;
    }

}
