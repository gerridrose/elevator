package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class ElevatorStateBase implements IElevatorControl {
    protected ElevatorContext elevatorContext;
    ElevatorStateBase previousElevatorState;

    ElevatorStateBase(ElevatorContext elevatorContext, ElevatorStateBase previousElevatorState) {
        this.elevatorContext = elevatorContext;
        this.previousElevatorState = previousElevatorState;
    }

    abstract public String getStateName();

    abstract protected void run();

    /**
     * Runs an algorithm to determine the next elevator state to move to based on
     * what it was previously doing and remaining requests (if any).
     * @return Next state to call run() on.
     */
    ElevatorStateBase decideNextState() {
        log.debug("Current state of all floor requests: {}", this.elevatorContext.getFloorRequests());
        // first find out if we were moving up or down previously
        // and continue that direction if requests follow those paths
//        if (this instanceof MovingUpState) {
//            // search upward in request list
//        }
        // TODO - build this algorithm out
        return new RestingState(this.elevatorContext, this);
    }
}
