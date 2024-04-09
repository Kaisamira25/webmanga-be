package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.dto.request.RCustomerAddressDTO;
import com.alpha.lainovo.model.Address;
import jakarta.servlet.http.HttpServletRequest;

public interface AddressInterface {
	Address getAddressByCustomer(HttpServletRequest request);
	Address addAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request);
	Address updateAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request);
}
