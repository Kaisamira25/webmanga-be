package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Orders;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findOrdersByCustomer(Customer customer);

    @Query("SELECT o FROM Orders o ORDER BY o.orderDay DESC")
    List<Orders> findAllOrderByOrderDay();
    List<Orders> findOrdersByOrderStatusAndPaymentStatus(String orderStatus,boolean payment);
}
