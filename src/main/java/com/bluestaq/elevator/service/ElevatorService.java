package com.bluestaq.elevator.service;

import com.bluestaq.elevator.dto.FloorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ElevatorService implements IElevatorControl {

    @Autowired
    ElevatorContext elevatorContext;

    @Value("${elevator.numFloors}")
    int numFloors;

    public void pressArrowUpOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);
        // edge validate that you cannot press arrow up on the highest floor number
        if (floorNumber == numFloors) {
            throw new IllegalArgumentException("Cannot press arrow up on the highest floor, floor " + floorNumber);
        }

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber - 1).arrowUp = true;

        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressArrowUpOnFloor(floorNumber);
    }

    public void pressArrowDownOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);
        // edge validate that you cannot press arrow down on the lowest floor number
        if (floorNumber == 1) {
            throw new IllegalArgumentException("Cannot press arrow down on the lowest floor, floor " + floorNumber);
        }

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber - 1).arrowDown = true;

        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressArrowDownOnFloor(floorNumber);
    }

    public void pressFloorNumber(int floorNumber) {
        validateFloorNumber(floorNumber);

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber - 1).requestedStop = true;

        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressFloorNumber(floorNumber);
    }

    public void pressCloseDoor() {
        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressCloseDoor();
    }

    public void pressOpenDoor() {
        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressOpenDoor();
    }

    void validateFloorNumber(int floorNumber) {
        if (floorNumber < 1 || floorNumber > numFloors) {
            throw new IllegalArgumentException("floorNumber must be between 1 and " + (numFloors - 1));
        }
    }

    public List<FloorDto> getActiveFloorRequests() {
        ArrayList<FloorDto> floorDtos = new ArrayList<>();

        for (int index = 0 ; index < elevatorContext.getFloorRequests().size(); index++) {
            FloorRequest floorRequest = elevatorContext.getFloorRequests().get(index);
            if (floorRequest.requestedStop || floorRequest.arrowUp || floorRequest.arrowDown) {
                floorDtos.add(new FloorDto(index + 1, floorRequest));
            }
        }

        return floorDtos;
    }
}
