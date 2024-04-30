package com.alpha.lainovo.repository;

import com.alpha.lainovo.dto.request.RCustomerDTO;
import com.alpha.lainovo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);

    @Query("SELECT new com.alpha.lainovo.dto.request.RCustomerDTO" +
            "(c.fullName, c.email , a.phoneNumber, a.address, c.isBlocked) " +
            "FROM Customer c JOIN c.addresses a WHERE c.email LIKE %:email%")
    List<RCustomerDTO> getCustomerByEmail (String email);
    Customer findByRefreshToken(String token);

    Customer findByCustomerResetPasswordCode(String customerResetPasswordCode);
}
