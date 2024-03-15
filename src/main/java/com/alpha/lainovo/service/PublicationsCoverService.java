package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.CoverRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsCoverService {

    private final PublicationsRepository publicationsRepo;

    private final CoverRepository coverRepo;

    @Transactional
    public void addCoverToPublications(Integer publicationsId, Integer coverId) {
        Publications publications = publicationsRepo.findById(publicationsId).get();
        Cover cover = coverRepo.findById(coverId).get();
        publications.getCovers().add(cover);
        publicationsRepo.save(publications);
        log.info(">>>>>> PublicationsCoverServiceImp:addCoverToPublications | Added Cover with id:{} to Publications with id:{} ", coverId, publicationsId);
    }

//    @Transactional
//    public boolean removeGenreFromPublications(Integer publicationsId, Integer genreId) {
//        Optional<Publications> optionalPublications = publicationsRepo.findById(publicationsId);
//        Optional<Genre> optionalGenre = genreRepo.findById(genreId);
//
//        if (optionalPublications.isPresent() && optionalGenre.isPresent()) {
//            Publications publications = optionalPublications.get();
//            Genre genre = optionalGenre.get();
//
//            Genre genreToRemove = publications.getGenres().stream()
//                    .filter(g -> Objects.equals(g.getGenreID(), genre.getGenreID()))
//                    .findFirst()
//                    .orElse(null);
//
//            if (genreToRemove != null) {
//                publications.getGenres().remove(genreToRemove);
//                publicationsRepo.save(publications);
//                log.info(">>>>>> PublicationsServiceImp:removeGenreFromPublications | Successfully removed genre with id: {} from publications with id: {}", genreId, publicationsId);
//                return true;
//            } else {
//                log.error(">>>>>>> PublicationsServiceImp:removeGenreFromPublications | Failed to remove genre with id: {} from publications with id: {}. Genre not found in the publication's genres.", genreId, publicationsId);
//            }
//        } else {
//            log.error(">>>>>>> PublicationsServiceImp:removeGenreFromPublications | No Publications found with id: {} or no Genre found with id: {}", publicationsId, genreId);
//        }
//        return false;
//    }
//


}
