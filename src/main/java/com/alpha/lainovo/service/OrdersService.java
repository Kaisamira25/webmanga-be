package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Orders;
import com.alpha.lainovo.repository.OrdersRepository;
import com.alpha.lainovo.service.ServiceInterface.OrdersInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService implements OrdersInterface {
    private final OrdersRepository repository;

    @Override
    public List<Orders> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Orders> findbyCustomer(Customer customer) {
        return repository.findOrdersByCustomer(customer);
    }


    @Override
    public Object create(Integer entity) {
        return null;
    }

    @Override
    public Integer update(Orders key, Integer entity) {
        return null;
    }
}
