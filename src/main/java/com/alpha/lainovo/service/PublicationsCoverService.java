package com.alpha.lainovo.service;

import com.alpha.lainovo.entityutils.EntityUtils;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.CoverRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsCoverService {

    private final PublicationsRepository publicationsRepo;

    private final CoverRepository coverRepo;

    @Transactional
    public void addCoverToPublications(Integer publicationsId, Integer coverId) {
        Publications publications = publicationsRepo.findByPublicationsID(publicationsId).get();
        Cover cover = coverRepo.findByCoverID(coverId).get();
        publications.getCovers().add(cover);
        publicationsRepo.save(publications);
        log.info(">>>>>> PublicationsCoverServiceImp:addCoverToPublications | Added Cover with id:{} to Publications with id:{} ", coverId, publicationsId);
    }

    @Transactional
    public boolean removeCoverFromPublications(Integer publicationsId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);

        if (optionalPublications.isPresent() ) {
            Publications publications = optionalPublications.get();
            publications.setCovers(null);
                publicationsRepo.save(publications);
                return true;
        } else {
            log.error(">>>>>>> PublicationsCoverServiceImp:removeGenreFromPublications | No Publications found with id: {}", publicationsId);
        }
        return false;
    }


}
