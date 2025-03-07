package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovingUpState extends ElevatorStateBase {
    MovingUpState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "MOVING UP";
    }

    @Override
    public void run() {
        // moved up
        try {
            Thread.sleep(elevatorContext.floorToFloorTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        elevatorContext.setCurrentFloor(elevatorContext.getCurrentFloor() + 1);

        if (elevatorContext.getCurrentFloor() < elevatorContext.numFloors) {

        } else if (elevatorContext.getCurrentFloor() == elevatorContext.numFloors) {
            // reached the top, go to door open since there must be a reason we reached here

        } else {
            log.error("Elevator's current floor is out of bounds!");
        }
        log.info("Moving to floor {}...", this.getStateName());

        // set next state to run at the end of this run()
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
