package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoverRepository extends JpaRepository<Cover, Integer> {

    Optional<Cover> findByCoverID (Integer id);

    List<Cover> findCoversByCoverTypeContains(String coverType);
}
