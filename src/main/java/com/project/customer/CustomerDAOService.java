package com.project.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAOService implements CustomerDAO{
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        customers.add(new Customer(1, 21, "Alex", "alex@gmail.com"));
        customers.add(new Customer(2, 22, "Tom", "tom@gmail.com"));
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
}
