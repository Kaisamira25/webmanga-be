package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByGenre(String genre);
    Optional<Genre> findByGenreID(Integer id);
}
