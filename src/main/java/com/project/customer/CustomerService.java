package com.project.customer;  // BUSINESS LAYER

import com.project.Exception.DuplicateResource;
import com.project.Exception.ResourceNotFound;
import com.project.Exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // creating a 'CustomerService' bean in Application context, go to customerController.java
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO) {
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

    public void deleteCustomerById(Integer custId){
        if(!customerDAO.idExists(custId)){
            throw new ResourceNotFound("Customer with %s not found".formatted(custId));
        }
        customerDAO.deleteCustomerById(custId);
    }

    public void updateCustomer(Integer custId, CustomerUpdateRequest request){
        Customer c = getCustomerById(custId);

        boolean changes = false;

        if(request.name() != null && !request.name().equals(c.getName())){
            c.setName(request.name());
            changes = true;
        }

        if(request.age() != null && request.age() != c.getAge()){
            c.setAge(request.age());
            changes = true;
        }

        if(request.email() != null && request.email().equals(c.getEmail())){
            throw new DuplicateResource("Email already taken");
        }
        else{
            c.setEmail(request.email());
            changes = true;
        }

        if(!changes) throw new ResourceValidationException("No changes found");

        customerDAO.insertCustomer(c);
    }
}
