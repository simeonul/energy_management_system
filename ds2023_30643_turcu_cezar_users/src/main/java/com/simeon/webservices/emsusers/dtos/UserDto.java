package com.simeon.webservices.emsusers.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;

    @Pattern(regexp = "^(?i)[a-z ,.'-]+$", message = "Invalid name format")
    private String name;

    private String email;

    private String password;

    @Pattern(regexp = "^(?i)(admin|client)$", message = "The role should be either admin or client")
    private String role;

    public UserDto(UUID id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
