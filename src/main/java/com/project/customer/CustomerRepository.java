package com.project.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public boolean existsCustomersByEmail(String email);
    public boolean existsCustomersById(Integer id);
    public Optional<Customer> findUserByEmail(String email);
}
