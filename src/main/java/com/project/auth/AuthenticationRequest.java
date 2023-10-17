package com.project.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
