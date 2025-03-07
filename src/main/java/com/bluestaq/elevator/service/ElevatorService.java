package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElevatorService implements IElevatorControl {

    @Autowired
    ElevatorContext elevatorContext;

    public void pressArrowUpOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber).arrowUp = true;

        elevatorContext.getElevatorState().pressArrowUpOnFloor(floorNumber);
    }

    public void pressArrowDownOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber).arrowDown = true;

        elevatorContext.getElevatorState().pressArrowUpOnFloor(floorNumber);
    }

    public void pressFloorNumber(int floorNumber) {
        validateFloorNumber(floorNumber);

        // set the request for the appropriate floor
        elevatorContext.getFloorRequests().get(floorNumber).requestedFloor = true;

        elevatorContext.getElevatorState().pressArrowUpOnFloor(floorNumber);
    }

    public void pressCloseDoor() {
        elevatorContext.getElevatorState().pressCloseDoor();
    }

    public void pressOpenDoor() {
        elevatorContext.getElevatorState().pressOpenDoor();
    }

    private void validateFloorNumber(int floorNumber) {
        if (floorNumber < 1 || floorNumber > elevatorContext.numFloors) {
            throw new IllegalArgumentException("floorNumber must be between 1 and " + (elevatorContext.numFloors - 1));
        }
    }
}
