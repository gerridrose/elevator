package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
public class DoorOpenState extends ElevatorStateBase {
    DoorOpenState(ElevatorContext elevatorContext) {
        super(elevatorContext);
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
        this.elevatorContext.setElevatorStateBase(new RestingState(this.elevatorContext));
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
