package com.alpha.lainovo.service;

import com.alpha.lainovo.entityutils.EntityUtils;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicationsGenreService {

    private final PublicationsRepository publicationsRepo;

    private final GenreRepository genreRepo;

    @Transactional
    public void addGenreToPublications(Integer publicationsId, Integer genreId) {
        Optional<Publications> publications = publicationsRepo.findByPublicationsID(publicationsId);
        Genre genre = genreRepo.findByGenreID(genreId).get();
        publications.get().getGenres().add(genre);
        publicationsRepo.save(publications.get());
        log.info(">>>>>> PublicationsGenreServiceImp:addGenreToPublications | Added Genre with id:{} to Publications with id:{} ", genreId, publicationsId);
    }

    @Transactional
    public boolean removeGenreFromPublications(Integer publicationsId) {
        Optional<Publications> optionalPublications = publicationsRepo.findByPublicationsID(publicationsId);
        if (optionalPublications.isPresent() ) {
            Publications publications = optionalPublications.get();
                publications.setGenres(null);
                publicationsRepo.save(publications);
                return true;
        } else {
            log.error(">>>>>>> PublicationsServiceImp:removeGenreFromPublications | No Publications found with id: {}", publicationsId);
        }
        return false;
    }



}
