package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Publications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationsRepository extends JpaRepository<Publications, Integer> {

    Optional<Publications> findByPublicationsName(String name);
    Optional <Publications> findByPublicationsID(Integer id);


//    List<Publications> findByGenresID(Integer genreID);

//    @Query("SELECT new com.example.webappmanga.dto.request.RPublicationsGenreDTO(p.publicationsID, g.genreID) FROM Publications p JOIN p.genres g")
//    List<RPublicationsGenreDTO> findAllPublicationsGenres();
//
//    @Query("SELECT new com.example.webappmanga.dto.request.RPublicationsGiftDTO(p.publicationsID, gift.promotionalGiftID) FROM Publications p JOIN p.gifts gift")
//    List<RPublicationsGiftDTO> findAllPublicationsGifts();

}
