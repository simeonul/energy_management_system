package com.simeon.webservices.emsusers.controllers;

import com.simeon.webservices.emsusers.dtos.UserDto;
import com.simeon.webservices.emsusers.entities.User;
import com.simeon.webservices.emsusers.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class AuthenticationController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<URI> register(@Valid @RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User insertedUser = userService.insertUser(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/users")
                .path("/{id}")
                .buildAndExpand(insertedUser.getId())
                .toUri();
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @RequestMapping("/LogIn")
    public UserDto getUserAfterLogin(Authentication authentication){
        return userService.findUserByEmail(authentication.getName());
    }


}
