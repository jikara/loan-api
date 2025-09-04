package com.softmint.dto;

public record LoginRequest(String email, String password,boolean rememberMe) {
}