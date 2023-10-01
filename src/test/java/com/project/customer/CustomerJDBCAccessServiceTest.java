package com.project.customer;

import com.project.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCAccessServiceTest extends AbstractTestContainer {

    private CustomerJDBCAccessService jdbcAccessService;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        jdbcAccessService = new CustomerJDBCAccessService(
                getJDBCTemplate(),  // go to AbstractTestContainer
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "" + UUID.randomUUID()
        );
        jdbcAccessService.insertCustomer(customer);

        List<Customer> customers = jdbcAccessService.selectAllCustomers();

        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email
        );
        jdbcAccessService.insertCustomer(customer);

        Integer id = jdbcAccessService.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> c = jdbcAccessService.selectCustomerById(id);

        assertThat(c).isPresent().hasValueSatisfying(arg -> {
           assertThat(arg.getId()).isEqualTo(id);
           assertThat(arg.getName()).isEqualTo(customer.getName());
            assertThat(arg.getEmail()).isEqualTo(customer.getEmail());
            assertThat(arg.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void selectCustomerByIdReturnsEmpty(){
        int id = -1;

        Optional<Customer> c = jdbcAccessService.selectCustomerById(id);

        assertThat(c).isEmpty();
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void emailExists() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email
        );
        jdbcAccessService.insertCustomer(customer);

        boolean existsCustomer = jdbcAccessService.emailExists(email);

        assertThat(existsCustomer).isTrue();
    }

    @Test
    void deleteCustomerById() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email
        );
        jdbcAccessService.insertCustomer(customer);

        Integer id = jdbcAccessService.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        jdbcAccessService.deleteCustomerById(id);

        Optional<Customer> c = jdbcAccessService.selectCustomerById(id);
        assertThat(c).isNotPresent();
    }

    @Test
    void idExists() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email
        );
        jdbcAccessService.insertCustomer(customer);

        Integer id = jdbcAccessService.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean c = jdbcAccessService.idExists(id);

        assertThat(c).isTrue();
    }

    @Test
    void updateCustomer() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email
        );
        jdbcAccessService.insertCustomer(customer);

        Integer id = jdbcAccessService.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);
        update.setName("Foobar");

        jdbcAccessService.updateCustomer(update);

        Optional<Customer> c = jdbcAccessService.selectCustomerById(id);

        assertThat(c).isPresent().hasValueSatisfying(arg -> {
            assertThat(arg.getId()).isEqualTo(id);
            assertThat(arg.getName()).isEqualTo(update  .getName());
            assertThat(arg.getEmail()).isEqualTo(customer.getEmail());
            assertThat(arg.getAge()).isEqualTo(customer.getAge());
        });
    }

    // Create methods updateCustomerEmail(), updateCustomerAge(). Just change email, age in those methods
    // Rest everything is same as updateCustomer() method
}