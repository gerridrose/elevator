package com.bluestaq.elevator.service;

import org.springframework.stereotype.Component;

@Component
public class DoorOpenState extends ElevatorState {
    DoorOpenState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "DOOR OPEN";
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
