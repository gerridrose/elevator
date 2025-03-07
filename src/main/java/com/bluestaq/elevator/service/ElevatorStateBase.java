package com.bluestaq.elevator.service;

abstract public class ElevatorStateBase implements IElevatorControl {
    protected ElevatorContext elevatorContext;

    ElevatorStateBase(ElevatorContext elevatorContext) {
        this.elevatorContext = elevatorContext;
    }

    abstract public String getStateName();

    abstract protected void run();
}
