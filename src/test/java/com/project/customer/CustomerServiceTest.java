package com.project.customer;

import com.project.Exception.DuplicateResource;
import com.project.Exception.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // if you dont want to write autocloseable = MockitoAnnot...
class CustomerServiceTest {
    private CustomerService underTest;
    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();
    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO, passwordEncoder, customerDTOMapper);
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerDAO).selectAllCustomers();
    }

    @Test
    void getCustomerById() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com", Gender.MALE, "password");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        CustomerDTO expected = customerDTOMapper.apply(c);

        CustomerDTO actual = underTest.getCustomerById(id);

        assertThat(actual).isEqualTo(expected);
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
                        new CustomerRegistrationRequest("alex",
                                27,
                                email,
                                Gender.MALE,
                                "password");

        String passwordHash = "hcjyv756fjgv%^$JHGChg";

        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);

        underTest.addCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).insertCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getPassword()).isEqualTo(passwordHash);
    }

    @Test
    void emailExistsError() {
        String email = "alex@gmail.com";

        when(customerDAO.emailExists(email)).thenReturn(true);

        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest("alex", 27, email, Gender.MALE, "password");

        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResource.class)
                .hasMessage("Customer with %s already exists".formatted(request.email()));

        verify(customerDAO, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        int id = 1;

        when(customerDAO.idExists(id)).thenReturn(true);

        underTest.deleteCustomerById(id);

        verify(customerDAO).deleteCustomerById(id);
    }

    @Test
    void idExistsError(){
        int id = 10;
        when(customerDAO.idExists(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("Customer with %s not found".formatted(id));

        verify(customerDAO, never()).deleteCustomerById(id);
    }

    @Test
    void updateCustomer() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com", Gender.MALE, "password");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        CustomerUpdateRequest request =
                new CustomerUpdateRequest("Tom", 24,"tom@gmail.com", Gender.MALE);

        underTest.updateCustomer(id, request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
    }

    @Test
    void updateCustomerName() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com", Gender.MALE, "password");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        CustomerUpdateRequest request =
                new CustomerUpdateRequest("Tom", null,null, Gender.MALE);

        underTest.updateCustomer(id, request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getAge()).isEqualTo(c.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(c.getEmail());
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
    }

    @Test
    void updateCustomerAge() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com", Gender.MALE, "password");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        CustomerUpdateRequest request = new CustomerUpdateRequest(null, 24,null, Gender.MALE);

        underTest.updateCustomer(id, request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(c.getEmail());
        assertThat(capturedCustomer.getName()).isEqualTo(c.getName());
    }

    @Test
    void updateCustomerEmail() {
        int id = 1;
        Customer c = new Customer(id, 23, "Alex", "alex@hmail.com", Gender.MALE, "password");
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(c));

        CustomerUpdateRequest request =
                new CustomerUpdateRequest(null, null,"tom@gmail.com", Gender.MALE);

        underTest.updateCustomer(id, request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(captor.capture());

        Customer capturedCustomer = captor.getValue();

        assertThat(capturedCustomer.getAge()).isEqualTo(c.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getName()).isEqualTo(c.getName());
    }
}