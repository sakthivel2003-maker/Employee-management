package com.clarix.employeemanagement.service;

import com.clarix.employeemanagement.model.Address;
import com.clarix.employeemanagement.repository.AddressRepository;

public class AddressService {
    private AddressRepository addressRepository;

    public AddressRepository getAddressRepository() {
        if (null == addressRepository) {
            addressRepository = new AddressRepository();
        }
        return addressRepository;
    }

    public Address saveAddress(Address address) {
        return getAddressRepository().saveAddress(address);
    }
}