package com.project.auth;

import com.project.customer.CustomerDTO;

public record AuthenticationResponse(String jwtToken, CustomerDTO customerDTO) {
}
