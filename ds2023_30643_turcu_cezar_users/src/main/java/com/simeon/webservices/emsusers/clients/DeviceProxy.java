package com.simeon.webservices.emsusers.clients;

import com.simeon.webservices.emsusers.dtos.DeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "devices-client", url = "${client.devices}")
public interface DeviceProxy {

    @GetMapping("/devices")
    List<String> getAllDevices();

    @PostMapping("/devices")
    ResponseEntity<URI> insertDevice(@RequestBody DeviceDto deviceDto);

    @GetMapping("/devices/getDevicesByUserId/{userId}")
    ResponseEntity<List<DeviceDto>> getDevicesByUserId(@PathVariable UUID userId);

    @PutMapping("/devices/updateDevice/{deviceId}")
    ResponseEntity<DeviceDto> updateDevice(@PathVariable UUID deviceId, @RequestBody DeviceDto updatedDeviceDto);

    @DeleteMapping("/devices/deleteDevice/{deviceId}")
    ResponseEntity deleteDevice(@PathVariable UUID deviceId);

    @DeleteMapping("/devices/deleteUserId/{userId}")
    ResponseEntity deleteUserIds(@PathVariable UUID userId, @RequestHeader("Authorization") String authorizationHeader);
}
