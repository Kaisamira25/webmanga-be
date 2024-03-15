package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findByTypeName(String typeName);
    Optional<Type> findByTypeID(Integer id);
}
