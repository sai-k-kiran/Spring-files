package com.project.customer;  // BUSINESS LAYER

import org.springframework.stereotype.Service;
import java.util.List;

@Service // creating a 'service' type bean, go to customerController.java
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomerById(Integer custId){
        return customerDAO.selectCustomerById(custId)
                .orElseThrow(() -> new IllegalArgumentException("customer %s not found".formatted(custId)));
    }
}
