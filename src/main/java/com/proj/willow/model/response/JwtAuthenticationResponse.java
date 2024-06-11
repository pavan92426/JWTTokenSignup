package com.proj.willow.model.response;

public class JwtAuthenticationResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public JwtAuthenticationResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
