package com.softmint.dto;

public record ChangePasswordRequest(String currentPassword, String password,String confirmPassword) {
}
