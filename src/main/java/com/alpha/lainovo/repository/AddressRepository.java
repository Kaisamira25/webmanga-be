package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	
//	Address findByPhoneNumber(String phoneNumber);
}
