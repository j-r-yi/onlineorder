package com.laioffer.onlineorder.service;


import com.laioffer.onlineorder.entity.CustomerEntity;
import com.laioffer.onlineorder.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    // Dependency
    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public CustomerEntity getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
