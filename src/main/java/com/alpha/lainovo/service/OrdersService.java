package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.model.Orders;
import com.alpha.lainovo.repository.OrdersRepository;
import com.alpha.lainovo.service.ServiceInterface.OrdersInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService implements OrdersInterface {
    private final OrdersRepository repository;

    @Override
    public List<Orders> findAll() {
        List <Orders> list = repository.findAllOrderByOrderDay();
        Iterator<Orders> iterator = list.iterator();
        while(iterator.hasNext()) {
            Orders o = iterator.next();
            if (o.getOrderStatus().equals("Delivered!") && o.isPaymentStatus()) {
                iterator.remove(); // Loại bỏ phần tử thỏa mãn điều kiện
            }
        }
        return list;
    }

    @Override
    public List<Orders> findbyCustomer(Customer customer) {
        return repository.findOrdersByCustomer(customer);
    }

    @Override
    public Orders findbyId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> findbyStatus(String status, boolean statusPay) {
        return repository.findOrdersByOrderStatusAndPaymentStatus(status,statusPay);
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
