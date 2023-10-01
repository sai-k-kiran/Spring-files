package com.project.customer;

import com.project.Exception.DuplicateResource;
import com.project.Exception.ResourceNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // if you dont want to write autocloseable = MockitoAnnot...
class CustomerServiceTest {
    private CustomerService underTest;
    @Mock
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO);
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerDAO).selectAllCustomers();
    }

    @Test
    void getCustomerById() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        Customer test = underTest.getCustomerById(id);

        assertThat(test).isEqualTo(c);
    }

    @Test
    void willThrowErrorWhenCustomerOptional() {
        int id = 10;
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("Customer with [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        String email = "alex@gmail.com";

        when(customerDAO.emailExists(email)).thenReturn(false);

        CustomerRegistrationRequest request =
                        new CustomerRegistrationRequest("alex", 27, email);

        underTest.addCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).insertCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
    }

    @Test
    void emailExistsError() {
        String email = "alex@gmail.com";

        when(customerDAO.emailExists(email)).thenReturn(true);

        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest("alex", 27, email);

        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResource.class)
                .hasMessage("Customer with %s already exists".formatted(request.email()));

        verify(customerDAO, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}