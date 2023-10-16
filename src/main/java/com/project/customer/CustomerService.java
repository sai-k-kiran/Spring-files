package com.project.customer;  // BUSINESS LAYER

import com.project.Exception.DuplicateResource;
import com.project.Exception.ResourceNotFound;
import com.project.Exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service // creating a 'CustomerService' bean in Application context, go to customerController.java
public class CustomerService {
    private final CustomerDAO customerDAO;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO,
                           PasswordEncoder passwordEncoder,
                           CustomerDTOMapper customerDTOMapper) {
        this.customerDAO = customerDAO;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper;
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerDAO.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer custId){
        return customerDAO.selectCustomerById(custId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFound("Customer with [%s] not found".formatted(custId)));
    }

    public void addCustomer(CustomerRegistrationRequest regRequest){
        if(customerDAO.emailExists(regRequest.email()))
            throw new DuplicateResource("Customer with %s already exists".formatted(regRequest.email()));

        customerDAO.insertCustomer(new Customer(regRequest.age(),
                regRequest.name(),
                regRequest.email(),
                regRequest.gender(),
                passwordEncoder.encode(regRequest.password())));
    }

    public void deleteCustomerById(Integer custId){
        if(!customerDAO.idExists(custId)){
            throw new ResourceNotFound("Customer with %s not found".formatted(custId));
        }
        customerDAO.deleteCustomerById(custId);
    }

    public void updateCustomer(Integer custId, CustomerUpdateRequest request){
        Customer c = customerDAO.selectCustomerById(custId)
                .orElseThrow(() -> new ResourceNotFound("Customer with [%s] not found".formatted(custId)));

        boolean changes = false;

        if(request.name() != null && request.name().length() != 0 &&
                !request.name().equals(c.getName())){
            c.setName(request.name());
            changes = true;
        }

        if(request.age() != null && request.age() != c.getAge()){
            c.setAge(request.age());
            changes = true;
        }

        if(request.email() != null && request.email().equals(c.getEmail())){
            throw new DuplicateResource("Enter a new email ID");
        }
        else if(request.email() != null && request.email().length() != 0){
            c.setEmail(request.email());
            changes = true;
        }

        if(request.gender() != null && request.gender() != c.getGender()){
            c.setGender(request.gender());
            changes = true;
        }

        if(!changes) throw new ResourceValidationException("No changes found");

        customerDAO.updateCustomer(c);
    }
}
