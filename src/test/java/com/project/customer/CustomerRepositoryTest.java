package com.project.customer;

import com.project.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainer {
    @Autowired
    private CustomerRepository testRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void existsCustomersByEmail() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email,
                Gender.MALE);
        testRepository.save(customer);

        boolean c = testRepository.existsCustomersByEmail(email);

        assertThat(c).isTrue();
    }

    @Test
    void existsCustomersById() {
        String email = faker.internet().safeEmailAddress() + "" + UUID.randomUUID();
        Customer customer = new Customer(
                23,
                faker.name().fullName(),
                email,
                Gender.MALE);
        testRepository.save(customer);

        Integer id = testRepository.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean c = testRepository.existsCustomersById(id);

        assertThat(c).isTrue();
    }
}