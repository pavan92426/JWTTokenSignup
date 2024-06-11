package com.proj.willow.service;

import com.proj.willow.model.request.SignUpRequest;
import com.proj.willow.model.request.SigninRequest;
import com.proj.willow.model.response.JwtAuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface WillowUserService extends UserDetailsService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
