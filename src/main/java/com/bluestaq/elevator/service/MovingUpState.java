package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovingUpState extends ElevatorState {
    MovingUpState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "MOVING UP";
    }

    @Override
    public void run() {
        this.elevatorContext.setElevatorState(this);
        log.info("Moving elevator state to {}...", this.getStateName());

    }

    @Override
    public void pressArrowUpOnFloor(int floorNumber) {
        // see if we should stop there instead (if it is a closer upward stop)
    }

    @Override
    public void pressArrowDownOnFloor(int floorNumber) {
        // do nothing, we're moving up right now
    }

    @Override
    public void pressFloorNumber(int floorNumber) {
        // see if we should stop there instead (if it is a closer downward stop)
    }

    @Override
    public void pressCloseDoor() {
        // do nothing
    }

    @Override
    public void pressOpenDoor() {
        // do nothing
    }
}
