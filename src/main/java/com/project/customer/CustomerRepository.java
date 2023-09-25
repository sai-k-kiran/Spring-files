package com.project.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public boolean existsCustomersByEmail(String email);
    public boolean existsCustomersById(Integer id);
}
