package customer;  // DAO LAYER

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    public List<Customer> selectAllCustomers();
    public Optional<Customer> selectCustomerById(Integer custId);
}
