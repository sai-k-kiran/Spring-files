package com.project.customer;  // DAO LAYER


import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    public List<Customer> selectAllCustomers();
    public Optional<Customer> selectCustomerById(Integer custId);
    void insertCustomer(Customer customer);
    boolean emailExists(String email);

    void deleteCustomerById(Integer custId);
    boolean idExists(Integer id);

    void updateCustomer(Customer customer);
}
