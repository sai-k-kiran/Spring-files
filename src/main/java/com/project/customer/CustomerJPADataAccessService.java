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
    public Optional<Customer> selectUserByEmail(String email){
        return customerRepository.findUserByEmail(email);
    }
    @Override
    public void insertCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @Override
    public boolean emailExists(String email){
        return customerRepository.existsCustomersByEmail(email);
    }
    @Override
    public void deleteCustomerById(Integer custId){
        customerRepository.deleteById(custId);
    }
    @Override
    public boolean idExists(Integer custId){
        return customerRepository.existsCustomersById(custId);
    }
    @Override
    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
