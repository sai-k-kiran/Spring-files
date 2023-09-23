package com.project.customer;  // BUSINESS LAYER

import com.project.Exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // creating a 'CustomerService' bean in Application context, go to customerController.java
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomerById(Integer custId){
        return customerDAO.selectCustomerById(custId)
                .orElseThrow(() -> new ResourceNotFound("customer %s not found".formatted(custId)));
    }
}
