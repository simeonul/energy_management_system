package com.simeon.webservices.emsdevices.dtos;

import java.util.UUID;

public class UserDto {

    private UUID id;
    private String name;
    private String role;

    public UserDto(UUID id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}