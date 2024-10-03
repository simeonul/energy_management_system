package com.simeon.webservices.emsusers.controllers;

import com.simeon.webservices.emsusers.dtos.DeviceDto;
import com.simeon.webservices.emsusers.dtos.UserDto;
import com.simeon.webservices.emsusers.entities.User;
import com.simeon.webservices.emsusers.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<URI> insertUser(@Valid @RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User insertedUser = userService.insertUser(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insertedUser.getId())
                .toUri();
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/getAllUserIds")
    public ResponseEntity<List<UUID>> getAllUserIds() {
        List<UUID> userIds = userService.findAllUserIds();
        return new ResponseEntity<>(userIds, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") UUID userId) {
        UserDto userDto = userService.findUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto userDto = userService.findUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getDevicesByUserId/{userId}")
    public ResponseEntity<List<DeviceDto>> getDevicesByUserId(@PathVariable("userId") UUID userId) {
        List<DeviceDto> deviceDtoList = userService.findDevicesByUserId(userId);
        return new ResponseEntity<>(deviceDtoList, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") UUID userId) {
        String authorizationHeader = getRequestAuthorizationHeader();
        userService.deleteUser(userId, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Used for testing purposes. Would not be present in deployment
     * */
    @PostMapping("/unauthorizedInsert")
    public ResponseEntity<URI> unauthorizedInsertUser(@Valid @RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User insertedUser = userService.insertUser(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insertedUser.getId())
                .toUri();
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    private String getRequestAuthorizationHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader("Authorization");
        }
        return null;
    }
}
