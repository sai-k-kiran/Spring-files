package customer;   // API LAYER

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("api/v1/customers/{custId}") // returns customer with id = custId
    public Customer getCustomers(@PathVariable("custId") Integer custId){
        return customerService.getCustomerById(custId);
    }
}

// CustomerController -> CustomerService -> CustomerDAO -> CustomerDAOService -> CUstomer
