package com.alpha.lainovo.repository;

import com.alpha.lainovo.dto.request.PublicationsDetailsDTO;
import com.alpha.lainovo.dto.request.PublicationsHotPublicationsDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.PublicationsNewArrivalDTO;
import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface PublicationsRepository extends JpaRepository<Publications, Integer> {

        Optional<Publications> findByPublicationsName(String name);
        Optional <Publications> findByPublicationsID(Integer id);

        @Query("SELECT p FROM Publications p ORDER BY p.arrivalDay DESC")
        Page<Publications> getPagePublicationsWithImage(Pageable pageable);

        @Query("SELECT p FROM Publications p JOIN p.genres g WHERE g.genreID = :genre ORDER BY p.arrivalDay DESC")
        Page<Publications> getPagePublicationsWithImage(Pageable pageable,@Param("genre") Integer genre);
        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsHotPublicationsDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, i.imageURL) " +
                "FROM Publications p JOIN p.images i ORDER BY p.stock ASC LIMIT 6")
        List<PublicationsHotPublicationsDTO> getBestSellerPublicationsWithImage();

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsNewArrivalDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, i.imageURL) " +
                "FROM Publications p JOIN p.images i ORDER BY p.arrivalDay DESC LIMIT 6")
        List<PublicationsNewArrivalDTO> getNewArrivalPublicationsWithImage();

        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsImageDTO" +
                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, g.genre, t.typeName, i.imageURL) " +
                "FROM Publications p JOIN p.images i JOIN p.genres g JOIN p.types t")
        List<PublicationsImageDTO> getAllPublicationsWithImage();

//        @Query("SELECT new com.alpha.lainovo.dto.request.PublicationsImageDTO" +
//                "(p.publicationsID ,p.publicationsName,p.unitPrice,p.stock,p.author,p.publisher,p.publicationYear,p.summary,p.arrivalDay, g.genre, t.typeName, i.imageURL) " +
//                "FROM Publications p JOIN p.images i JOIN p.genres g JOIN p.types t where p.author = :name")
//        List<PublicationsImageDTO> getPublicationsWithAuthorName(@Param("name") String name);
        @Query("SELECT p FROM Publications p WHERE p.publicationsID = :id")
        Publications findPublicationsDetailsById(@Param("id") Integer id);
        List<Publications> getPublicationsByPublicationsNameContaining(String publicationsName);
        List<Publications> getPublicationsByAuthor(String authorName);  
        }
