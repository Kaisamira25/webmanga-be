package com.alpha.lainovo.repository;

import com.alpha.lainovo.dto.request.PublicationGenreDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.PublicationsWithGenreTypeDTO;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface PublicationsRepository extends JpaRepository<Publications, Integer> {

        Optional<Publications> findByPublicationsName(String name);
        Optional <Publications> findByPublicationsID(Integer id);

        @Query("SELECT p FROM Publications p ORDER BY p.arrivalDay DESC")
        Page<Publications> getPagePublicationsWithImage(Pageable pageable);

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsImageDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, i.imageURL) " +
                "FROM Publications p JOIN p.images i ORDER BY p.stock ASC")
        List<PublicationsImageDTO> getBestSellerPublicationsWithImage(Pageable pageable);

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsImageDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, i.imageURL) " +
                "FROM Publications p JOIN p.images i ORDER BY p.arrivalDay DESC")
        List<PublicationsImageDTO> getNewArrivalPublicationsWithImage(Pageable pageable);

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsImageDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, i.imageURL) " +
                "FROM Publications p JOIN p.images i")
        List<PublicationsImageDTO> getAllPublicationsWithImage();

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsWithGenreTypeDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, g.genre, t.typeName, i.imageURL) " +
                "FROM Publications p JOIN p.images i JOIN p.genres g JOIN p.types t")
        List<PublicationsWithGenreTypeDTO> getAllPublicationsWithGenreType();

        List<Publications> getPublicationsByPublicationsNameContaining(String publicationsName);

        }
