package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    List<Discount> findDiscountsByDiscountNameContains(String discountName);

    Discount findDiscountByDiscountCode(String code);

}
