package com.project.customer;

public record CustomerUpdateRequest(
        String name,
        Integer age,
        String email,
        Gender gender
) { }
