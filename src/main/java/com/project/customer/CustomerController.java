package com.project.customer;   // API LAYER

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    // spring is taking CustomerService bean which we have defined in 'CustomerService.java'
    @Autowired // optional in spring 3. Bean is injected automatically, no need of @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{custId}") // returns customer with id = custId
    public Customer getCustomers(@PathVariable("custId") Integer custId){
        return customerService.getCustomerById(custId);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
            customerService.addCustomer(request);
    }
    @DeleteMapping("{custId}")
    public void deleteCustomer(@PathVariable("custId") Integer custId){
        customerService.deleteCustomerById(custId);
    }

    @PutMapping("{custId}")
    public void updateCustomer(
            @PathVariable("custId") Integer custId, @RequestBody CustomerUpdateRequest request){
        customerService.updateCustomer(custId, request);
    }
}

// CustomerController -> CustomerService -> CustomerDAO -> CustomerDAOService -> Customer
