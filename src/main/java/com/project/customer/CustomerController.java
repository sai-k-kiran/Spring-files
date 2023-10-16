package com.project.customer;   // API LAYER

import com.project.jwt.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final JWTutil jwTutil;
    private final CustomerService customerService;
    // spring is taking CustomerService bean which we have defined in 'CustomerService.java'
    @Autowired // optional in spring 3. Bean is injected automatically, no need of @Autowired
    public CustomerController(CustomerService customerService, JWTutil jwTutil) {
        this.customerService = customerService;
        this.jwTutil = jwTutil;

    }

    @GetMapping
    public List<CustomerDTO> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{custId}") // returns customer with id = custId
    public CustomerDTO getCustomers(@PathVariable("custId") Integer custId){
        return customerService.getCustomerById(custId);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request){
            customerService.addCustomer(request);
            String jwtToken = jwTutil.issueToken(request.email(), "ROLE_USER");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .build();
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
