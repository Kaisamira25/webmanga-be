package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.repository.CustomerRepository;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInterface,CreateAndUpdateInterface<Integer, Customer> {
    private final CustomerRepository customerRepository;
    @Override
    public Object create(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public void update(Integer key, Customer entity) {
        customerRepository.save(entity);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findById(Integer userId) {
        Optional<Customer> user = customerRepository.findById(userId);
        return user.get();
    }

    @Override
    public Customer findByRefreshToken(String token) {
        return customerRepository.findByRefreshToken(token);
    }
}
