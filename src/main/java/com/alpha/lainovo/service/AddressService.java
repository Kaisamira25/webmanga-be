package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.RCustomerAddressDTO;
import com.alpha.lainovo.model.Address;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.repository.AddressRepository;
import com.alpha.lainovo.service.ServiceInterface.AddressInterface;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.service.ServiceInterface.ICreateAndUpdateV2;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService implements ICreateAndUpdateV2<Integer, Address>, AddressInterface {

    private final AddressRepository addressRepo;

    private final CustomerInterface customerInterface;

    private final GetCustomerIdByRequestService getCustomerIdByRequestService;

    private final CreateAndUpdateInterface<Integer, Customer> saveService;
    @Override
    public Object create(Address entity) {

        return addressRepo.save(entity);
    }

    @Override
    public Address update(Integer key, Address entity) {

        return addressRepo.save(entity);
    }

    @Override
    public Address getAddressByCustomer(HttpServletRequest request) {
        // Tìm khách hàng dựa trên customerId từ request
        Integer customerId = getCustomerIdByRequestService.getCustomerIdByRequest(request);
        Customer customer = customerInterface.findById(customerId);
        if (customer == null) {
            // Xử lý trường hợp không tìm thấy khách hàng
            log.error("Customer not found with id: {}", customerId);
            return null;
        }

        // Trả về địa chỉ của khách hàng
        log.info(">>>>>>> AddressServiceImp:getAddress | Get address for Customer with email: {}", customer.getEmail());
        return customer.getAddresses();
    }

    public Address addAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request) {
        // Tìm khách hàng dựa trên customerId từ request
        Integer customerId = getCustomerIdByRequestService.getCustomerIdByRequest(request);
        Customer customer = customerInterface.findById(customerId);
        if (customer == null) {
            // Xử lý trường hợp không tìm thấy khách hàng
            log.error("Customer not found with id: {}", customerId);
            return null;
        }
        // Tạo một đối tượng địa chỉ mới
        Address newAddress = new Address();
        newAddress.setPhoneNumber(addressDTO.phoneNumber());
        newAddress.setAddress(addressDTO.address());
        // Gán khách hàng cho địa chỉ
        newAddress.setCustomer(customer);
        // Lưu địa chỉ mới
        saveService.create(newAddress.getCustomer());
        // Gán địa chỉ mới cho khách hàng và lưu lại
        customer.setAddresses(newAddress);
        saveService.create(customer);
        log.info(">>>>>>> AddressServiceImp:addAddress | Add address for Customer with email: {}", customer.getEmail());
        // Trả về địa chỉ mới
        return newAddress;
    }


    @Override
    public Address updateAddress(RCustomerAddressDTO addressDTO, HttpServletRequest request) {
        // Tìm khách hàng dựa trên customerId từ request
        Integer customerId = getCustomerIdByRequestService.getCustomerIdByRequest(request);
        Customer customer = customerInterface.findById(customerId);
        if (customer == null) {
            // Xử lý trường hợp không tìm thấy khách hàng
            log.error("Customer not found with id: {}", customerId);
            return null;
        }

        // Cập nhật thông tin địa chỉ
        Address address = customer.getAddresses();
        if (addressDTO.phoneNumber() != null && !addressDTO.phoneNumber().isEmpty()) {
            address.setPhoneNumber(addressDTO.phoneNumber());
        }
        if (addressDTO.address() != null && !addressDTO.address().isEmpty()) {
            address.setAddress(addressDTO.address());
        }

        // Lưu khách hàng đã được cập nhật
        saveService.create(customer);

        log.info(">>>>>>> AddressServiceImp:updateAddress | Update address Customer with email: {}", customer.getEmail());

        // Trả về địa chỉ đã được cập nhật
        return address;
    }


}
