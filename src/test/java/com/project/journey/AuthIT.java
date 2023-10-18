package com.project.journey;

import com.project.auth.AuthenticationRequest;
import com.project.auth.AuthenticationResponse;
import com.project.customer.CustomerDTO;
import com.project.customer.CustomerRegistrationRequest;
import com.project.customer.Gender;
import com.project.jwt.JWTutil;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTutil jwTutil;

    private static final Random RANDOM = new Random();
    private static final String CUSTOMER_PATH = "/api/v1/customers";
    private static final String AUTH_PATH = "/api/v1/auth";

    @Test
    void canLogin() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = name.toLowerCase().replaceAll(" ", "_") + "@gmail.com";
        String password = "password";

        int age = RANDOM.nextInt(1, 100);
        Gender gender = (age % 2 == 0) ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequest(name, age, email, gender, password);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                email,
                password
        );

        webTestClient.post()
                .uri(AUTH_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerRegistrationRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        WebTestClient.BodySpec<AuthenticationResponse, ?> spec = webTestClient.post()
                .uri(AUTH_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                });

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTH_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk().
                expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();

        String token = result.getResponseHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        AuthenticationResponse authenticationResponse = result.getResponseBody();
        CustomerDTO customerDTO = authenticationResponse.customerDTO();

        assertThat(jwTutil.isValidToken(token, customerDTO.username())).isTrue();

        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.name()).isEqualTo(name);
        assertThat(customerDTO.username()).isEqualTo(email);
        assertThat(customerDTO.gender()).isEqualTo(gender);
        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));
    }
}
