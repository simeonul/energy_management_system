package com.simeon.webservices.emsusers.services;
import com.simeon.webservices.emsusers.clients.DeviceProxy;
import com.simeon.webservices.emsusers.dtos.DeviceDto;
import com.simeon.webservices.emsusers.dtos.UserDto;
import com.simeon.webservices.emsusers.dtos.builders.UserBuilder;
import com.simeon.webservices.emsusers.entities.User;
import com.simeon.webservices.emsusers.entities.enums.UserRole;
import com.simeon.webservices.emsusers.exceptions.models.DuplicateValueException;
import com.simeon.webservices.emsusers.exceptions.models.ResourceNotFoundException;
import com.simeon.webservices.emsusers.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DeviceProxy deviceProxy;

    @Autowired
    public UserService(UserRepository userRepository, DeviceProxy deviceProxy) {
        this.userRepository = userRepository;
        this.deviceProxy = deviceProxy;
    }

    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDto)
                .collect(Collectors.toList());
    }

    public List<UUID> findAllUserIds() {
        return userRepository.findAll().stream().map(user -> user.getId()).collect(Collectors.toList());
    }

    public UserDto findUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserBuilder::toUserDto)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} was not found in the database", id);
                    return new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
                });
    }

    public UserDto findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserBuilder::toUserDto)
                .orElseThrow(() -> {
                    LOGGER.error("User with email {} was not found in the database", email);
                    return new ResourceNotFoundException(User.class.getSimpleName() + " with email: " + email);
                });
    }

    public User insertUser(UserDto userDto) {
        User user = UserBuilder.toEntity(userDto);
        try {
            user = userRepository.save(user);
            LOGGER.debug("User with id {} was inserted in db", user.getId());
        }catch (Exception ex){
            throw new DuplicateValueException(user.getEmail());
        }
        return user;
    }

    public void deleteUser(UUID userId, String authorizationHeader){
        deleteUserIds(userId, authorizationHeader);
        userRepository.deleteById(userId);
    }

    public UserDto updateUser(UUID userId, UserDto updatedUser){
        User originalUser = userRepository.findById(userId).get();
        originalUser.setEmail(updatedUser.getEmail());
        originalUser.setName(updatedUser.getName());
        originalUser.setRole(UserRole.valueOf(updatedUser.getRole()));
        userRepository.save(originalUser);
        LOGGER.debug("User with id {} was updated in db", userId);
        return UserBuilder.toUserDto(originalUser);
    }

    public List<DeviceDto> findDevicesByUserId(UUID uuid){
        ResponseEntity<List<DeviceDto>> proxyResponse = deviceProxy.getDevicesByUserId(uuid);
        if (proxyResponse.getStatusCode() == HttpStatus.OK) {
            return proxyResponse.getBody();
        } else {
            throw new RuntimeException("Could not perform call to getAllUsers");
        }
    }

    public void deleteUserIds(UUID uuid, String authorizationHeader) {
        ResponseEntity proxyResponse = deviceProxy.deleteUserIds(uuid, authorizationHeader);
        if (proxyResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Could not perform call to deleteUserIds");
        }
    }
}
