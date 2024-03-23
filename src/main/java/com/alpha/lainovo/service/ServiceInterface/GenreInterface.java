package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreInterface extends ICreateAndUpdateV2<Integer, Genre> {

    boolean delete(Integer id);

    Optional<Genre> findByGenre(String genreName);
    Genre getByGenreId(Integer id);
    List<Genre> getAllGenre();

    List<Genre> getGenreListbyGenre(String genre);
}
