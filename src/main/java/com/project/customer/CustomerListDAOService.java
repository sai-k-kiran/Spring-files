package com.project.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDAOService implements CustomerDAO{
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        customers.add(new Customer(1, 21, "Alex", "alex@gmail.com", Gender.MALE, "password"));
        customers.add(new Customer(2, 22, "Tom", "tom@gmail.com", Gender.MALE, "password"));
    }

    @Override
    public List<Customer> selectAllCustomers(){
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer custId){
        return customers.stream()
                .filter(c -> c.getId().equals(custId))
                .findFirst();
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email){
        return customers.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }
    @Override
    public void insertCustomer(Customer customer){
        customers.add(customer);
    }

    @Override
    public boolean emailExists(String email){
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }
    @Override
    public void deleteCustomerById(Integer custId){
        customers.stream()
                .filter(c -> c.getId().equals(custId))
                .findFirst()
                .ifPresent(c -> customers.remove(c));
    }
    @Override
    public boolean idExists(Integer custId){
        return customers.stream()
                .anyMatch(c -> c.getId().equals(custId));
    }
    @Override
    public void updateCustomer(Customer customer){
        customers.add(customer);
    }
}
