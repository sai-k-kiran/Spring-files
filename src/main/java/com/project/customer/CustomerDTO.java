package com.project.customer;

import java.util.List;

public record CustomerDTO(
    Integer id,
    String name,
    Integer age,
    String email,
    Gender gender,
    List<String> roles,
    String username
) { }
