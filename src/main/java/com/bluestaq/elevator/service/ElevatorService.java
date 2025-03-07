package com.bluestaq.elevator.service;

import com.bluestaq.elevator.dto.FloorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ElevatorService implements IElevatorControl {

    @Autowired
    ElevatorContext elevatorContext;

    public void pressArrowUpOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);
        // edge validate that you cannot press arrow up on the highest floor number
        if (floorNumber == elevatorContext.numFloors) {
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
        elevatorContext.getFloorRequests().get(floorNumber - 1).requestedFloor = true;

        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressArrowUpOnFloor(floorNumber);
    }

    public void pressCloseDoor() {
        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressCloseDoor();
    }

    public void pressOpenDoor() {
        // have the running state check if it needs to immediate action on this
        elevatorContext.getElevatorStateBase().pressOpenDoor();
    }

    private void validateFloorNumber(int floorNumber) {
        if (floorNumber < 1 || floorNumber > elevatorContext.numFloors) {
            throw new IllegalArgumentException("floorNumber must be between 1 and " + (elevatorContext.numFloors - 1));
        }
    }

    public List<FloorDto> getActiveFloorRequests() {
        ArrayList<FloorDto> floorDtos = new ArrayList<>();

        for (int index = 0 ; index < elevatorContext.getFloorRequests().size(); index++) {
            Floor floor = elevatorContext.getFloorRequests().get(index);
            if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                floorDtos.add(new FloorDto(index + 1, floor));
            }
        }

        return floorDtos;
    }
}
