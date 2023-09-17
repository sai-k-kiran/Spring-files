package customer;  // BUSINESS LAYER

import java.util.List;

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
