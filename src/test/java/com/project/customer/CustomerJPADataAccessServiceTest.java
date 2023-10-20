package com.project.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        Page<Customer> page = mock(Page.class);
        List<Customer> customers = List.of(new Customer());
        when(page.getContent()).thenReturn(customers);

        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<Customer> expected = underTest.selectAllCustomers();

        assertThat(expected).isEqualTo(customers);
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(customerRepository).findAll(captor.capture());
        assertThat(captor.getValue()).isEqualTo(Pageable.ofSize(10));
    }

    @Test
    void selectCustomerById() {
        int id = 1;
        underTest.selectCustomerById(id);

        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer customer = new Customer(1, 27, "Tom", "tom@email.com", Gender.MALE, "password");
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
        Customer customer = new Customer(1, 27, "Tom", "tom@email.com", Gender.MALE, "password");
        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer);
    }
}