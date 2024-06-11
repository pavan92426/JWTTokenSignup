package com.proj.willow.service.impl;

import com.proj.willow.entity.Role;
import com.proj.willow.entity.WillowUserEntity;
import com.proj.willow.model.request.SignUpRequest;
import com.proj.willow.model.request.SigninRequest;
import com.proj.willow.model.response.ErrorMessages;
import com.proj.willow.model.response.JwtAuthenticationResponse;
import com.proj.willow.repository.WillowUserRepository;
import com.proj.willow.service.JwtService;
import com.proj.willow.service.WillowUserService;
import com.proj.willow.shared.utils.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class WillowUserServiceImpl implements WillowUserService {

    @Autowired
    WillowUserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("custom :: user serviceimpl loadUserByUsername request");
        WillowUserEntity existingUser = userRepository.findByEmail(username);
        if(existingUser == null) throw new UsernameNotFoundException(username);
        return new User(username,existingUser.getPassword(),new ArrayList<>());
    }

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        log.info("custom :: user serviceimpl signup request");
        var existingUserCheck = userRepository.findByEmail(request.getEmail());
        if(existingUserCheck != null){
            throw new UserServiceException("Record already exists");
        }
        WillowUserEntity user = new WillowUserEntity();
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        var result = userRepository.save(user);
        var jwt = jwtService.generateToken(result);
        var res = new JwtAuthenticationResponse().setToken(jwt);
        return res;
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        log.info("custom :: user serviceimpl signin request");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail());
        if (user == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse().setToken(jwt);
    }
}
