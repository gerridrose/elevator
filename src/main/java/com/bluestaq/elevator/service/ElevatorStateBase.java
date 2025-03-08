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
        // we are going to want the current floor requests in a lot of cases
        // (remember to -1 when converting to array reference)
        FloorRequest currentFloorRequest = elevatorContext.getFloorRequests().get(elevatorContext.getCurrentFloor() - 1);

        // find out what state we were just in and use the previous state info when needed
        switch (this) {
            case MovingUpState movingUpState -> {
                ElevatorStateBase nextState = decideStateAfterMoveUp(currentFloorRequest);
                if (nextState != null)
                    return nextState;
            }
            case MovingDownState movingDownState -> {
                ElevatorStateBase nextState = decideStateAfterMoveDown(currentFloorRequest);
                if (nextState != null)
                    return nextState;
            }
            case DoorOpenState doorOpenState -> {
                // find out what direction we were moving previously
                // and first search for requests along that path before searching
                // the other direction
                if (this.previousElevatorState instanceof MovingUpState) {
                    ElevatorStateBase nextState = decideStateAfterMoveUp(currentFloorRequest);
                    if (nextState != null)
                        return nextState;
                } else if (this.previousElevatorState instanceof MovingDownState) {
                    ElevatorStateBase nextState = decideStateAfterMoveDown(currentFloorRequest);
                    if (nextState != null)
                        return nextState;
                }
            }
            default -> {
                return new RestingState(this.elevatorContext, this);
            }
        }
        return new RestingState(this.elevatorContext, this);
    }

    private ElevatorStateBase decideStateAfterMoveDown(FloorRequest currentFloorRequest) {
        // check if the floor we just moved to has a request for going in this direction
        if (currentFloorRequest.requestedStop || currentFloorRequest.arrowDown) {
            // we need to open door next
            return new DoorOpenState(elevatorContext, this);
        }

        // the floor we just moved to does not have a request, start from the one below it now
        for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
             floorIndex >= 0; floorIndex--) {
            FloorRequest floorRequest = elevatorContext.getFloorRequests().get(floorIndex);
            if (floorRequest.requestedStop || floorRequest.arrowUp || floorRequest.arrowDown) {
                // further down request found we need to continue moving down
                return new MovingDownState(elevatorContext, this);
            }
        }

        // check if we have moved downards to an arrow up request before changing elevator directions
        if (currentFloorRequest.arrowUp) {
            // we need to open door next
            return new DoorOpenState(elevatorContext, this);
        }

        // search the downward direction for ANY requests
        // before letting elevator go to resting state
        for (int floorIndex = elevatorContext.getCurrentFloor();
             floorIndex < elevatorContext.numFloors; floorIndex++) {
            FloorRequest floorRequest = elevatorContext.getFloorRequests().get(floorIndex);
            if (floorRequest.requestedStop || floorRequest.arrowUp || floorRequest.arrowDown) {
                // further up request found we need to continue moving up
                return new MovingUpState(elevatorContext, this);
            }
        }
        return null;
    }

    private ElevatorStateBase decideStateAfterMoveUp(FloorRequest currentFloorRequest) {
        // check if the floor we just moved to has a request going in this direction
        if (currentFloorRequest.requestedStop || currentFloorRequest.arrowUp) {
            // we need to open door next
            return new DoorOpenState(elevatorContext, this);
        }

        // the floor we just moved to does not have a request, start from the one above it now
        for (int floorIndex = elevatorContext.getCurrentFloor();
             floorIndex < elevatorContext.numFloors; floorIndex++) {

            FloorRequest floorRequest = elevatorContext.getFloorRequests().get(floorIndex);
            if (floorRequest.requestedStop || floorRequest.arrowUp || floorRequest.arrowDown) {
                // further up request found we need to continue moving up
                return new MovingUpState(elevatorContext, this);
            }
        }

        // check if we have moved upwards to an arrow down request before changing elevator directions
        if (currentFloorRequest.arrowDown) {
            // we need to open door next
            return new DoorOpenState(elevatorContext, this);
        }

        // search the downward direction for ANY requests
        // before letting elevator go to resting state
        for (int floorIndex = elevatorContext.getCurrentFloor() - 2;
             floorIndex >= 0; floorIndex--) {
            FloorRequest floorRequest = elevatorContext.getFloorRequests().get(floorIndex);
            if (floorRequest.requestedStop || floorRequest.arrowDown || floorRequest.arrowUp) {
                // further down request found we need to continue moving down
                return new MovingDownState(elevatorContext, this);
            }
        }
        return null;
    }
}
