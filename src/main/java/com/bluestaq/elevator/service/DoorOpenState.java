package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
public class DoorOpenState extends ElevatorStateBase {
    DoorOpenState(ElevatorContext elevatorContext, ElevatorStateBase previousElevatorState) {
        super(elevatorContext, previousElevatorState);
    }

    private boolean closeDoorRequested = false;
    private boolean openDoorRequested = false;

    @Override
    public String getStateName() {
        return "DOOR OPEN";
    }

    @Override
    public void run() {
        log.info("Elevator door opened on floor {}.", this.elevatorContext.getCurrentFloor());
        FloorRequest floorRequests = this.elevatorContext.getFloorRequests().get(elevatorContext.getCurrentFloor() - 1);

        // was this a requested floor?  if so, clear the request
        if (floorRequests.requestedStop) {
            log.info("Someone requested to GET OFF at this floor.  Clearing the request now.");
            floorRequests.requestedStop = false;
        }

        // what direction were we moving before this?  clear the arrow request if matching
        if (this.previousElevatorState instanceof MovingUpState && floorRequests.arrowUp) {
            log.info("Someone requested ARROW UP on this floor.  Clearing the request now.");
            floorRequests.arrowUp = false;
        }

        if (this.previousElevatorState instanceof MovingDownState && floorRequests.arrowDown) {
            log.info("Someone requested ARROW DOWN on this floor.  Clearing the request now.");
            floorRequests.arrowDown = false;
        }

        if (this.previousElevatorState instanceof MovingUpState && floorRequests.arrowDown) {
            log.info("Someone requested ARROW DOWN while elevator was MOVING UP on this floor but elevator has no " +
                    "additional requests in the direction.  Clearing the request now.");
            floorRequests.arrowDown = false;
        }

        if (this.previousElevatorState instanceof MovingDownState && floorRequests.arrowUp) {
            log.info("Someone requested ARROW UP while elevator was MOVING DOWN on this floor but elevator has no " +
                    "additional requests in the direction.  Clearing the request now.");
            floorRequests.arrowUp = false;
        }

        if (this.previousElevatorState instanceof RestingState) {
            // if we were resting before and just opened the doors, clear all the requests on this floor
            floorRequests.requestedStop = false;
            floorRequests.arrowUp = false;
            floorRequests.arrowDown = false;
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        while (stopWatch.getDuration().toMillis() < this.elevatorContext.doorOpenTime) {
            // periodically check for any requests
            if (closeDoorRequested) {
                log.info("Received request to close door early.");
                // immediately leave this loop
                closeDoorRequested = false;
                break;
            } else if (openDoorRequested) {
                log.info("Received request to keep door open, resetting door open timer.");
                openDoorRequested = false;
                stopWatch.reset();
                stopWatch.start();
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    log.error("Ran into an interruption while trying to Thread.sleep() due to {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }

        log.info("Elevator door closed on floor {}.", this.elevatorContext.getCurrentFloor());
        // run elevator algorithm to see what state is next that all states should do here
        // and set next state to run at the end of this run()
        this.elevatorContext.setElevatorStateBase(this.decideNextState());
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
        this.closeDoorRequested = true;
    }

    @Override
    public void pressOpenDoor() {
        this.openDoorRequested = true;
    }
}
