package com.project.customer;

public record CustomerRegistrationRequest(
        String name,
        Integer age,
        String email
) {
}
