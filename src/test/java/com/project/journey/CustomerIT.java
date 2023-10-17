package com.project.journey;

import com.project.customer.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;
    private static final Random RANDOM = new Random();
    private static final String CUSTOMER_PATH = "/api/v1/customers";

    @Test
    void canRegisterCustomer() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = name.toLowerCase().replaceAll(" ", "_") + "@gmail.com";
        int age = RANDOM.nextInt(1, 100);
        Gender gender = (age % 2 == 0) ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(name, age, email, gender, "password");

        String JWTtoken = webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        List<CustomerDTO> response = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", JWTtoken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {})
                .returnResult()
                .getResponseBody();

        int id = response.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO :: id)
                .findFirst()
                .orElseThrow();

        CustomerDTO expected  = new CustomerDTO(
                id,
                name,
                age,
                email,
                gender,
                List.of("ROLE_USER"),
                email
        );

        assertThat(response).contains(expected);


        CustomerDTO actual = webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", JWTtoken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected); // this will fail unless you have override "equals()" method of
    }                                           // Customer. See Customer.java NOTE 1

    @Test
    void canDeleteCutomer() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = name.toLowerCase().replaceAll(" ", "_") + "@gmail.com";
        int age = RANDOM.nextInt(1, 100);
        Gender gender = (age % 2 == 0) ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(name, age, email, gender, "password");

        CustomerRegistrationRequest request2 =
                new CustomerRegistrationRequest(name, age, email + ".in", gender, "password");

        webTestClient.post() // customer to be deleted
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        String JWTtoken = webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request2), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        List<CustomerDTO> response = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",JWTtoken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {})
                .returnResult()
                .getResponseBody();


        int id = response.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO :: id)
                .findFirst()
                .orElseThrow();

        webTestClient.delete()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",JWTtoken))
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",JWTtoken))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = name.toLowerCase().replaceAll(" ", "_") + "@gmail.com";
        int age = RANDOM.nextInt(1, 100);
        Gender gender = (age % 2 == 0) ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(name, age, email, gender, "password");


        String JWTtoken = webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        List<CustomerDTO> response = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", JWTtoken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {})
                .returnResult()
                .getResponseBody();


        int id = response.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO :: id)
                .findFirst()
                .orElseThrow();

        String newName = "Tom";
        CustomerUpdateRequest updateRequest =
                new CustomerUpdateRequest(newName, null, null, null);

        webTestClient.put()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",JWTtoken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        CustomerDTO updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",JWTtoken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();
        CustomerDTO expected = new CustomerDTO(id, newName, age, email, gender, List.of("ROLE_USER"), email);

        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
