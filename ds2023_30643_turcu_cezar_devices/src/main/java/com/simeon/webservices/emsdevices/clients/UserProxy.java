package com.simeon.webservices.emsdevices.clients;

import com.simeon.webservices.emsdevices.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "users-service", url = "${client.users}")
public interface UserProxy {

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/users/getAllUserIds")
    ResponseEntity<List<UUID>> getAllUserIds();
}
