package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestingState extends ElevatorState implements IElevatorControl  {
    RestingState(ElevatorContext elevatorContext) {
        super(elevatorContext);
    }

    @Override
    public String getStateName() {
        return "RESTING";
    }

    @Override
    public void run() {
        // No run work to do in this state, elevator is resting
    }

    @Override
    public void pressArrowUpOnFloor(int floorNumber) {
        startElevator(new MovingUpState(this.elevatorContext));
    }

    @Override
    public void pressArrowDownOnFloor(int floorNumber) {
        startElevator(new MovingDownState(this.elevatorContext));
    }

    @Override
    public void pressFloorNumber(int floorNumber) {
        if (this.elevatorContext.getCurrentFloor() > floorNumber) {
            // if floor number is lower than current floor, need to start moving down
            startElevator(new MovingDownState(this.elevatorContext));
        } else if (this.elevatorContext.getCurrentFloor() == floorNumber) {
            // if floor number matches current floor, just need to open the door
            startElevator(new DoorOpenState(this.elevatorContext));
        } else {
            // assuming floorNumber is higher than current floor at this point
            startElevator(new MovingUpState(this.elevatorContext));
        }
    }

    @Override
    public void pressCloseDoor() {
        // if resting, there is an action really does nothing
        log.warn("Pressed close door while in {} state.  While not invalid, this action will do nothing.",
                this.getStateName());
    }

    @Override
    public void pressOpenDoor() {
        startElevator(new DoorOpenState(this.elevatorContext));
    }

    private void startElevator(ElevatorState newElevatorState) {
        // Create and start a thread to simulate the elevator using the lambda approach
        Thread elevatorThread = new Thread(() -> {
            log.info("Starting elevator on floor {}.",
                    this.elevatorContext.getCurrentFloor());

            try {
                newElevatorState.run();
                Thread.sleep(50);

                // done fulfilling all active requests
                this.elevatorContext.setElevatorState(this);

            } catch (InterruptedException e) {
                log.warn("Elevator simulation was interrupted! {}", e.getMessage());
            }

            // RESTING state was entered again to reach this point, this signals to end this elevator thread
            log.info("No remaining active requests for elevator.  Moving it back to the {} state...", this.getStateName());
        });

        elevatorThread.start();
    }
}
