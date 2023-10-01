package com.project.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {
    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        underTest.selectAllCustomers();

        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        int id = 1;
        underTest.selectCustomerById(id);

        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer customer = new Customer(1, 27, "Tom", "tom@email.com");
        underTest.insertCustomer(customer);

        verify(customerRepository).save(customer);
    }

    @Test
    void emailExists() {
        String email = "foo@gmail.com";

        underTest.emailExists(email);

        verify(customerRepository).existsCustomersByEmail(email);
    }

    @Test
    void deleteCustomerById() {
        int id = 1;

        underTest.deleteCustomerById(id);

        verify(customerRepository).deleteById(id);
    }

    @Test
    void idExists() {
        int id = 1;

        underTest.idExists(id);

        verify(customerRepository).existsCustomersById(id);
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(1, 27, "Tom", "tom@email.com");
        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer);
    }
}