package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersInterface extends ICreateAndUpdateV2<Orders,Integer> {
    List<Orders> findAll();
    List<Orders> findbyCustomer(Customer customer);

    Orders findbyId(Integer id);

    List<Orders> findbyStatus(String status,boolean statusPay);
}
