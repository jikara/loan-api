package com.softmint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(@JsonProperty("access_token") String token,
                            @JsonProperty("refresh_token") String refreshToken,
                            @JsonProperty("token_type") String tokenType) {
}
