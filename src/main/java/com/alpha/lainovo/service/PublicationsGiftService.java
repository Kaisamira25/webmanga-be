package com.alpha.lainovo.service;

import com.alpha.lainovo.entityutils.EntityUtils;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.PromotionalGiftRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsGiftService {

    private final PublicationsRepository publicationsRepo;

    private final PromotionalGiftRepository giftRepo;

    @Transactional
    public void addGiftToPublications(Integer publicationsId, Integer giftId) {
        Publications publications = publicationsRepo.findByPublicationsID(publicationsId).get();
        PromotionalGift gift = giftRepo.findByPromotionalGiftID(giftId).get();
        publications.getGifts().add(gift);
        publicationsRepo.save(publications);
        log.info(">>>>>> PublicationsGiftServiceImp:addGiftToPublications | Added Gift with id:{} to Publications with id:{} ", giftId, publicationsId);
    }

    @Transactional
    public boolean removeGiftFromPublications(Integer publicationsId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);
        if (optionalPublications.isPresent()) {
            Publications publications = optionalPublications.get();
                publications.setGifts(null);
                publicationsRepo.save(publications);
                return true;
        } else {
            return false;
        }
    }



}
