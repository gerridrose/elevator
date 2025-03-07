package com.bluestaq.elevator.service;

abstract public class ElevatorState implements IElevatorControl {
    protected ElevatorContext elevatorContext;

    ElevatorState (ElevatorContext elevatorContext) {
        this.elevatorContext = elevatorContext;
    }

    abstract public String getStateName();

    abstract protected void run();
}
