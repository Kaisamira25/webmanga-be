package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreInterface extends CreateAndUpdateInterface<Integer, Genre> {

    boolean delete(Integer id);

    Optional<Genre> findByGenre(String genreName);
    Genre getByGenreId(Integer id);
    List<Genre> getAllGenre();
}
