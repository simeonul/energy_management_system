package com.simeon.webservices.emsusers.dtos.builders;

import com.simeon.webservices.emsusers.dtos.UserDto;
import com.simeon.webservices.emsusers.entities.User;
import com.simeon.webservices.emsusers.entities.enums.UserRole;

import java.util.Locale;

public class UserBuilder {

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }

    public static User toEntity(UserDto userDto){
        return new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                UserRole.valueOf(userDto.getRole().toUpperCase(Locale.ROOT)));
    }
}
