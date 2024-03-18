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
    public boolean removeCoverFromPublications(Integer publicationsId, Integer coverId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);
        Optional<Cover> optionalCover = coverRepo.findByCoverID(coverId);

        if (optionalPublications.isPresent() && optionalCover.isPresent()) {
            Publications publications = optionalPublications.get();
            Cover cover = optionalCover.get();
//            publications.getGenres().forEach(g -> {
//                System.out.println("Genre ID: " + g.getGenreID());
//            });
            if (publications.getCovers().stream().anyMatch(c -> EntityUtils.equals(c, cover))) {
                publications.getCovers().removeIf(c -> EntityUtils.equals(c, cover));
                publicationsRepo.save(publications);
                return true;
            } else {
                return false;
            }
        } else {
            log.error(">>>>>>> PublicationsCoverServiceImp:removeGenreFromPublications | No Publications found with id: {} or no Cover found with id: {}", publicationsId, coverId);
        }
        return false;
    }


}
