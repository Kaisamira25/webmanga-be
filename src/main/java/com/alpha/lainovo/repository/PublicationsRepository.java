package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Publications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface PublicationsRepository extends JpaRepository<Publications, Integer> {

        Optional<Publications> findByPublicationsName(String name);
        Optional <Publications> findByPublicationsID(Integer id);

        @Query("SELECT p FROM Publications p ORDER BY p.arrivalDay DESC")
        Page<Publications> getPagePublications(Pageable pageable);

        @Query("SELECT p FROM Publications p ORDER BY p.stock ASC")
        List<Publications> getBestSellerPublications(Pageable pageable);

        @Query("SELECT p FROM Publications p ORDER BY p.arrivalDay DESC")
        List<Publications> getNewArrivalPublications(Pageable pageable);

    }
