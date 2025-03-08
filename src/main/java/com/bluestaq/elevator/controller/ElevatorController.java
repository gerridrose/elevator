package com.bluestaq.elevator.controller;

import com.bluestaq.elevator.dto.FloorDto;
import com.bluestaq.elevator.service.ElevatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@Tag(name = "Elevator Requests", description = "Requests available for controlling the elevator.")
public class ElevatorController {

    @Autowired
    ElevatorService elevatorService;

    // Open door request
    @Operation(summary = "Press Open Door button in Elevator",
            description = "Press Open Door button in Elevator")
    @GetMapping("/pressOpenDoor")
    public void requestOpenDoor() {
        elevatorService.pressOpenDoor();
    }

    // Close door request
    @Operation(summary = "Press Close Door button in Elevator",
            description = "Press Close Door button in Elevator")
    @GetMapping("/pressCloseDoor")
    public void requestCloseDoor() {
        elevatorService.pressCloseDoor();
    }

    // Press floor number request
    @Operation(summary = "Press any floor button in Elevator",
            description = "Press any floor button in Elevator")
    @GetMapping("/pressFloorNumber")
    public void requestFloorNumber(@RequestParam int floorNumber) {
        elevatorService.pressFloorNumber(floorNumber);
    }

    // Press arrow up on some floor
    @Operation(summary = "Press Arrow Up button on Elevator floor",
            description = "Press Arrow Up button on Elevator floor")
    @GetMapping("/pressArrowUp")
    public void requestArrowUp(@RequestParam int floorNumber) {
        elevatorService.pressArrowUpOnFloor(floorNumber);
    }

    // Press arrow down on some floor
    @Operation(summary = "Press Arrow Down button on Elevator floor",
            description = "Press Arrow Down button on Elevator floor")
    @GetMapping("/pressArrowDown")
    public void requestArrowDown(@RequestParam int floorNumber) {
        elevatorService.pressArrowDownOnFloor(floorNumber);
    }

    // Get all the floor requests active
    @Operation(summary = "Queries Elevator for all active requests it is tracking",
            description = "Queries Elevator for all active requests it is tracking")
    @GetMapping("/activeFloorRequests")
    public List<FloorDto> getActiveFloorRequests() {
        return elevatorService.getActiveFloorRequests();
    }
}
