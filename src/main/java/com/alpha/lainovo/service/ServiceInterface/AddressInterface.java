package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.RCustomerAddressDTO;
import com.alpha.lainovo.model.Address;
import com.alpha.lainovo.model.Customer;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressInterface {
	Address getAddressByCustomer(HttpServletRequest request);
	Address addAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request);
	Address updateAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request);
	Address getAddressByCustomerId(Integer customerId);
	List<Address> getAllAddress();
}
