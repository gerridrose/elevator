package com.bluestaq.elevator.service;

import org.springframework.stereotype.Component;

@Component
public class RestingState extends ElevatorState {
    RestingState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "RESTING";
    }

    @Override
    public void pressUpOnFloor(int floorNumber) {

    }

    @Override
    public void pressDownOnFloor(int floorNumber) {

    }

    @Override
    public void pressFloorNumber(int floorNumber) {

    }

    @Override
    public void pressCloseDoor() {

    }

    @Override
    public void pressOpenDoor() {

    }
}
