package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Publications;
import com.alpha.lainovo.model.Type;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.service.ServiceInterface.GenreInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService implements GenreInterface {
    @Autowired
    private PublicationsRepository publicationsRepository;

    private final GenreRepository genreRepo;

    @Override
    @Cacheable(cacheNames = "Genre", key = "'#id'")
    public Genre getByGenreId(Integer id) {
        return genreRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Genre", key = "'#genreList'")
    public List<Genre> getAllGenre() {
        return genreRepo.findAll();
    }

    @Override
    public List<Genre> getGenreListbyGenre(String genre) {
        return genreRepo.findGenresByGenreContains(genre);
    }



    @Override
    public Object create(Genre genreDTO) {
        Genre genres = new Genre();
        genres.setGenre(genreDTO.getGenre());
        genreRepo.save(genres);
        log.info(">>>>>> GenreServiceImp:save | Create a Genre with name:{} ", genres.getGenre());
        return genres;
    }

    @Override
    @Cacheable(cacheNames = "Genre", key = "'#id'")
    public Genre update(Integer id, Genre genresDTO) {
        Genre genres = getByGenreId(id);
        if (genres != null) {
            genres.setGenre(genresDTO.getGenre());
             genreRepo.save(genres);
            log.info(">>>>>> GenreServiceImp:update | Update a Genre with name:{} ", genres.getGenre());
            return genres;
        }
        log.error(">>>>>>> GenreServiceImp:update | No Genre found to update with id: {} ",id);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Genre", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        Genre genres = getByGenreId(id);
        if (genres != null) {
            genreRepo.delete(genres);
            return true;
        }
        return false;
    }
    public Set<Genre> getGenresByPublicationId(Integer publicationId) {
        Set<Genre> genres = new HashSet<>();
        Optional<Publications> optionalPublication = publicationsRepository.findById(publicationId);

        if (optionalPublication.isPresent()) {
            Publications publication = optionalPublication.get();
            genres = publication.getGenres();
        }
        return genres;
    }

}
