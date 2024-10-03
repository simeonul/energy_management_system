package com.simeon.webservices.emsdevices.controllers;

import com.simeon.webservices.emsdevices.dtos.DeviceDto;
import com.simeon.webservices.emsdevices.entities.Device;
import com.simeon.webservices.emsdevices.services.DeviceService;
import com.simeon.webservices.emsdevices.services.MonitoringDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/devices")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeviceController {

    private final DeviceService deviceService;

    private final MonitoringDeviceService monitoringDeviceService;

    @Autowired
    public DeviceController(DeviceService deviceService, MonitoringDeviceService monitoringDeviceService) {
        this.deviceService = deviceService;
        this.monitoringDeviceService = monitoringDeviceService;
    }

    @PostMapping()
    public ResponseEntity<URI> insertDevice(@RequestBody DeviceDto deviceDto){
        Device insertedDevice = deviceService.insertDevice(deviceDto);
        monitoringDeviceService.insertDevice(insertedDevice);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(insertedDevice.getId())
                .toUri();
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDto>> getAllDevices(){
        List<DeviceDto> deviceList = deviceService.findAllDevices();
        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }

    @GetMapping("/getDevicesByUserId/{userId}")
    public ResponseEntity<List<DeviceDto>> getDevicesByUserId(@PathVariable UUID userId){
        List<DeviceDto> deviceList = deviceService.findAllDeviceByUserId(userId);
        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }

    @PutMapping("/updateDevice/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable UUID deviceId, @RequestBody DeviceDto updatedDeviceDto) {
        DeviceDto updatedDevice = deviceService.updateDevice(deviceId, updatedDeviceDto);
        monitoringDeviceService.updateDevice(updatedDevice);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDevice/{deviceId}")
    public ResponseEntity deleteDevice(@PathVariable UUID deviceId) {
        DeviceDto deletedDevice = deviceService.findDeviceById(deviceId);
        deviceService.deleteDevice(deviceId);
        monitoringDeviceService.deleteDevice(deletedDevice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserId/{userId}")
    public ResponseEntity deleteUserIds(@PathVariable UUID userId) {
        List<DeviceDto> devices = deviceService.findAllDeviceByUserId(userId);
        deviceService.deleteUserIds(devices);
        monitoringDeviceService.deleteUserIds(devices);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
