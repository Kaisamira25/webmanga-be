package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersItemRepository extends JpaRepository<OrderItem,Integer> {
}
