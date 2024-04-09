package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.Publications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findAll();
    List<Image> findAllByPublications(Publications id);

    void deleteImageByImageID(Integer id);

}
