package com.softmint.dto;

import java.io.Serializable;

public record ResetPasswordRequest(String token, String password) implements Serializable {
}
