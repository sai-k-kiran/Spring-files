package com.project.customer;  // BUSINESS LAYER

import com.project.Exception.DuplicateResource;
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

    public void addCustomer(CustomerRegistrationRequest regRequest){
        if(customerDAO.emailExists(regRequest.email()))
            throw new DuplicateResource("Customer with %s already exists".formatted(regRequest.email()));

        customerDAO.insertCustomer(new Customer(regRequest.age(), regRequest.name(), regRequest.email()));
    }
}
