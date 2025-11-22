package com.example.simulacoes.api.config.security.auth;

public record AuthRequest(
    String username,
    String password
) {}