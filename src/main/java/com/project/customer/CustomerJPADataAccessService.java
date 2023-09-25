package com.project.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO{
    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers(){
        return customerRepository.findAll();
    }
    @Override
    public Optional<Customer> selectCustomerById(Integer custId){
        return customerRepository.findById(custId);
    }

    @Override
    public boolean emailExists(String email){
        return customerRepository.existsCustomersByEmail(email);
    }
}