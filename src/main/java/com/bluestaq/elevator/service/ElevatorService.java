package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElevatorService implements IElevatorControl {

    @Autowired
    ElevatorContext elevatorContext;

    public void pressUpOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);

        elevatorContext.getFloorRequests().get(floorNumber).arrowUp = true;
        // IF RESTING, SHOULD SPIN OFF THREAD
        elevatorContext.getElevatorState().pressUpOnFloor(floorNumber);
    }

    public void pressDownOnFloor(int floorNumber) {
        validateFloorNumber(floorNumber);

        elevatorContext.getFloorRequests().get(floorNumber).arrowDown = true;

        // IF RESTING, SHOULD SPIN OFF THREAD
        elevatorContext.getElevatorState().pressUpOnFloor(floorNumber);
    }

    public void pressFloorNumber(int floorNumber) {
        validateFloorNumber(floorNumber);

        elevatorContext.getFloorRequests().get(floorNumber).requestedFloor = true;

        // IF RESTING, SHOULD SPIN OFF THREAD
        elevatorContext.getElevatorState().pressUpOnFloor(floorNumber);
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
