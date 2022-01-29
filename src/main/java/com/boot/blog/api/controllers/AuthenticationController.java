package com.boot.blog.api.controllers;

import com.boot.blog.api.models.RoleModel;
import com.boot.blog.api.models.UserModel;
import com.boot.blog.api.payload.JWtAuthResponse;
import com.boot.blog.api.payload.LoginDto;
import com.boot.blog.api.payload.SignUpDto;
import com.boot.blog.api.repository.RoleRepository;
import com.boot.blog.api.repository.UserRepository;
import com.boot.blog.api.security.JWTtokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTtokenProvider jwTtokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWtAuthResponse> authUser(@RequestBody LoginDto loginDto){
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);
       String token = jwTtokenProvider.generateToken(authentication);
       return ResponseEntity.ok(new JWtAuthResponse(token));
    }
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Bad Request , Username already exists",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Bad Request , Email already exists",HttpStatus.BAD_REQUEST);
        }
        UserModel user = new UserModel();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setUsername(signUpDto.getUsername());
        RoleModel roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("Signed Up",HttpStatus.OK);
    }

}
