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
        //log.debug("Current state of all floor requests: {}", this.elevatorContext.getFloorRequests());

        // we are going to want the current floor requests in a lot of cases
        // (remember to -1 when converting to array reference)
        Floor currentFloor = elevatorContext.getFloorRequests().get(elevatorContext.getCurrentFloor() - 1);

        // first find out if we were moving up or down previously
        switch (this.previousElevatorState) {
            case MovingUpState movingUpState -> {
                // check if the floor we just moved to has a request
                if (currentFloor.requestedFloor || currentFloor.arrowUp || currentFloor.arrowDown) {
                    // we need to open door next
                    return new DoorOpenState(elevatorContext, this);
                }

                // the floor we just moved to does not have a request, start from the one above it now
                for (int floorIndex = elevatorContext.getCurrentFloor();
                     floorIndex < elevatorContext.numFloors; floorIndex++) {
                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                        // further up request found we need to continue moving up
                        return new MovingUpState(elevatorContext, this);
                    }
                }
                // nothing found in this direction, search the other direction for ANY requests
                // before letting elevator go to resting state
                for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
                     floorIndex >= 0; floorIndex--) {
                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowDown || floor.arrowUp) {
                        // further down request found we need to continue moving down
                        return new MovingDownState(elevatorContext, this);
                    }
                }
            }
            case MovingDownState movingDownState -> {
                // check if the floor we just moved to has a request
                // (remember to -1 when converting to array reference)

                if (currentFloor.requestedFloor || currentFloor.arrowDown || currentFloor.arrowUp) {
                    // we need to open door next
                    return new DoorOpenState(elevatorContext, this);
                }

                // the floor we just moved to does not have a request, start from the one below it now
                for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
                     floorIndex >= 0; floorIndex--) {
                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                        // further down request found we need to continue moving down
                        return new MovingDownState(elevatorContext, this);
                    }
                }

                // nothing found in this direction, search the other direction for ANY requests
                // before letting elevator go to resting state
                for (int floorIndex = elevatorContext.getCurrentFloor();
                     floorIndex < elevatorContext.numFloors; floorIndex++) {
                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                        // further up request found we need to continue moving up
                        return new MovingUpState(elevatorContext, this);
                    }
                }
            }
            case DoorOpenState doorOpenState -> {
                // find out what direction we were moving previously
                // and first search for requests along that path before searching
                // the other direction
                if (this.previousElevatorState.previousElevatorState instanceof MovingUpState) {
                    // the floor we just moved to does not have a request, start from the one above it now
                    for (int floorIndex = elevatorContext.getCurrentFloor();
                         floorIndex < elevatorContext.numFloors; floorIndex++) {
                        Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                        if (floor.requestedFloor || floor.arrowUp) {
                            // further up request found we need to continue moving up
                            return new MovingUpState(elevatorContext, this);
                        }
                    }
                    // nothing found in this direction, search the other direction for ANY requests
                    // before letting elevator go to resting state
                    for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
                         floorIndex >= 0; floorIndex--) {
                        Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                        if (floor.requestedFloor || floor.arrowDown || floor.arrowUp) {
                            // further down request found we need to continue moving down
                            return new MovingDownState(elevatorContext, this);
                        }
                    }
                } else if (this.previousElevatorState.previousElevatorState instanceof MovingDownState) {
                    // the floor we just moved to does not have a request, start from the one below it now
                    for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
                         floorIndex >= 0; floorIndex--) {
                        Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                        if (floor.requestedFloor || floor.arrowUp) {
                            // further down request found we need to continue moving down
                            return new MovingDownState(elevatorContext, this);
                        }
                    }

                    // nothing found in this direction, search the other direction for ANY requests
                    // before letting elevator go to resting state
                    for (int floorIndex = elevatorContext.getCurrentFloor();
                         floorIndex < elevatorContext.numFloors; floorIndex++) {
                        Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                        if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                            // further up request found we need to continue moving up
                            return new MovingUpState(elevatorContext, this);
                        }
                    }
                }
            }
            case null, default -> {
                // we'll search for ANY work on ANY floor,
                // our current floor first,
                // then searching upward before downward,
                // otherwise let go back to resting
                if (currentFloor.requestedFloor || currentFloor.arrowUp || currentFloor.arrowDown) {
                    return new DoorOpenState(elevatorContext, this);
                }

                // REMEMBER: getCurrentFloor() when used on getFloorRequests() will actually be the floor above
                // due to array indexing
                for (int floorIndex = elevatorContext.getCurrentFloor();
                     floorIndex < elevatorContext.numFloors; floorIndex++) {

                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowUp || floor.arrowDown) {
                        // further up request found we need to continue moving up
                        return new MovingUpState(elevatorContext, this);
                    }
                }
                // nothing found in this direction, search the other direction for ANY requests
                // before letting elevator go to resting state
                for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
                     floorIndex >= 0; floorIndex--) {
                    Floor floor = elevatorContext.getFloorRequests().get(floorIndex);
                    if (floor.requestedFloor || floor.arrowDown || floor.arrowUp) {
                        // further down request found we need to continue moving down
                        return new MovingDownState(elevatorContext, this);
                    }
                }
            }
        }

        return new RestingState(this.elevatorContext, this);
    }
}
