package com.bluestaq.elevator.service;

import java.util.Optional;

abstract public class ElevatorState implements IElevatorControl {
    private ElevatorContext elevatorContext;

    ElevatorState (ElevatorContext elevatorContext) {
        this.elevatorContext = elevatorContext;
    }

    abstract public String getStateName();
}
