package com.bluestaq.elevator.service;

import org.springframework.stereotype.Component;

@Component
public class MovingDownState extends ElevatorState {
    MovingDownState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "MOVING DOWN";
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
