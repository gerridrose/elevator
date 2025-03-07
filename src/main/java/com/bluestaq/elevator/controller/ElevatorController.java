package com.bluestaq.elevator.controller;

import com.bluestaq.elevator.dto.FloorDto;
import com.bluestaq.elevator.service.ElevatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@Tag(name = "Elevator Requests", description = "Requests available for controlling the elevator.")
public class ElevatorController {

    @Autowired
    ElevatorService elevatorService;

    // Open door request
    @GetMapping("/openDoor")
    public void requestOpenDoor() {
        elevatorService.pressOpenDoor();
    }

    // Close door request
    @GetMapping("/closeDoor")
    public void requestCloseDoor() {
        elevatorService.pressCloseDoor();
    }

    // Press floor number request
    @GetMapping("/floorNumber")
    public void requestFloorNumber(@RequestParam int floorNumber) {
        elevatorService.pressFloorNumber(floorNumber);
    }

    // Press arrow up on some floor
    @GetMapping("/arrowUp")
    public void requestArrowUp(@RequestParam int floorNumber) {
        elevatorService.pressArrowUpOnFloor(floorNumber);
    }

    // Press arrow down on some floor
    @GetMapping("/arrowDown")
    public void requestArrowDown(@RequestParam int floorNumber) {
        elevatorService.pressArrowDownOnFloor(floorNumber);
    }

    // Get all the floor requests active
    @GetMapping("/activeFloorRequests")
    public List<FloorDto> getActiveFloorRequests() {
        return elevatorService.getActiveFloorRequests();
    }
}
