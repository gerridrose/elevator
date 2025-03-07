package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoorOpenState extends ElevatorState {
    DoorOpenState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "DOOR OPEN";
    }

    @Override
    public void run() {
        this.elevatorContext.setElevatorState(this);
        log.info("Moving elevator state to {}...", this.getStateName());
    }

    @Override
    public void pressArrowUpOnFloor(int floorNumber) {
        // no action
    }

    @Override
    public void pressArrowDownOnFloor(int floorNumber) {
        // do nothing
    }

    @Override
    public void pressFloorNumber(int floorNumber) {
        // do nothing
    }

    @Override
    public void pressCloseDoor() {
        // end door open timer and switch to moving up or down state
    }

    @Override
    public void pressOpenDoor() {
        // reset door open timer
    }
}
