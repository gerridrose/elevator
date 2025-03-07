package com.bluestaq.elevator.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class ElevatorContext {
    // TODO - if wanting to create more than one elevator, need to have an associated ID field
    // and not make this a singleton instance (use spring prototype instead)

    // Config values
    @Value("${elevator.numFloors}")
    int numFloors;

    @Value("${elevator.time.floorToFloor}")
    int floorToFloorTime;

    @Value("${elevator.time.doorOpen}")
    int doorOpenTime;

    /**
     * Current floor of the elevator is on.
     */
    @Getter
    @Setter
    private int currentFloor = 1;

    /**
     * Current state of the elevator.  Initialized in the RESTING state.
     * <br/><br/>
     * NOTE: Since RestingState does not depend on the previous state it was in, the first time it is provided null.
     */
    private ElevatorStateBase elevatorStateBase = new RestingState(this, null);

    // No lombok for elevatorState getter and setter to wrap with synchronized
    // and log state changes easily in one place
    public synchronized ElevatorStateBase getElevatorStateBase() {
        return elevatorStateBase;
    }

    public synchronized void setElevatorStateBase(ElevatorStateBase elevatorStateBase) {
        log.info("Moving elevator state to {}.", elevatorStateBase.getStateName());
        this.elevatorStateBase = elevatorStateBase;
    }

    /**
     * Holds all the request states for every floor in a presorted list (based on how it was created).
     */
    @Getter
    private ArrayList<Floor> floorRequests = new ArrayList<>();

    @PostConstruct
    void postConstruct() {
        // create all the floor objects for the floorStates member variable
        // we have to do this here because the spring context needs to have already been loaded
        // with the @Value param
        for (int i = 0; i < numFloors; i++) {
            floorRequests.add(new Floor());
        }
        log.info("Created {} floors for this elevator simulation.", numFloors);

        log.info("Elevator in the {} state on floor {}.", elevatorStateBase.getStateName(), currentFloor);
    }
}
