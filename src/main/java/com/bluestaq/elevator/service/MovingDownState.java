package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovingDownState extends ElevatorStateBase {
    MovingDownState(ElevatorContext elevatorContext, ElevatorStateBase previousElevatorState) {
        super(elevatorContext, previousElevatorState);
    }

    @Override
    public String getStateName() {
        return "MOVING DOWN";
    }

    @Override
    public void run() {
        log.info("Moving elevator DOWN...");

        // simulate moving down
        try {
            Thread.sleep(elevatorContext.floorToFloorTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // -1 because we are moving down
        elevatorContext.setCurrentFloor(elevatorContext.getCurrentFloor() - 1);

        log.info("Moved to floor {}...", elevatorContext.getCurrentFloor());

        // run elevator algorithm to see what state is next that all states should do here
        // and set next state to run at the end of this run()
        this.elevatorContext.setElevatorStateBase(this.decideNextState());
    }

    @Override
    public void pressArrowUpOnFloor(int floorNumber) {
        // do nothing, we're moving down right now
    }

    @Override
    public void pressArrowDownOnFloor(int floorNumber) {
        // see if we should stop there instead (if it is a closer downward stop)
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
