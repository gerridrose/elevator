package com.bluestaq.elevator.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestingState extends ElevatorStateBase implements IElevatorControl  {
    RestingState(ElevatorContext elevatorContext, ElevatorStateBase previousElevatorState) {
        super(elevatorContext, previousElevatorState);
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
        // just because user pressed arrow up, we could be above the floor they pressed that
        // on and may need to move down
        if (floorNumber < elevatorContext.getCurrentFloor()) {
            startElevator(new MovingDownState(this.elevatorContext, this));
        } else if (floorNumber == elevatorContext.getCurrentFloor()) {
            // just open the door, we're on the floor they pressed the arrow on
            startElevator(new DoorOpenState(this.elevatorContext, this));
        } else {
            startElevator(new MovingUpState(this.elevatorContext, this));
        }
    }

    @Override
    public void pressArrowDownOnFloor(int floorNumber) {
        // just because user pressed arrow down, we could be below the floor they pressed that
        // on and may need to move down
        if (floorNumber < elevatorContext.getCurrentFloor()) {
            startElevator(new MovingDownState(this.elevatorContext, this));
        } else if (floorNumber == elevatorContext.getCurrentFloor()) {
            // just open the door, we're on the floor they pressed the arrow on
            startElevator(new DoorOpenState(this.elevatorContext, this));
        } else {
            startElevator(new MovingUpState(this.elevatorContext, this));
        }
    }

    @Override
    public void pressFloorNumber(int floorNumber) {
        if (this.elevatorContext.getCurrentFloor() > floorNumber) {
            // if floor number is lower than current floor, need to start moving down
            startElevator(new MovingDownState(this.elevatorContext, this));
        } else if (this.elevatorContext.getCurrentFloor() == floorNumber) {
            // if floor number matches current floor, just need to open the door
            startElevator(new DoorOpenState(this.elevatorContext, this));
        } else {
            // assuming floorNumber is higher than current floor at this point since floor number
            // is validated before here
            startElevator(new MovingUpState(this.elevatorContext, this));
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
        startElevator(new DoorOpenState(this.elevatorContext, this));
    }

    private void startElevator(ElevatorStateBase nextElevatorStateBase) {
        // Create and start a thread to simulate the elevator using the lambda approach
        Thread elevatorThread = new Thread(() -> {
            log.info("Starting elevator on floor {}.",
                    this.elevatorContext.getCurrentFloor());

            this.elevatorContext.setElevatorStateBase(nextElevatorStateBase);

            do {
                this.elevatorContext.getElevatorStateBase().run();
            } while (!(this.elevatorContext.getElevatorStateBase() instanceof RestingState));

            // RESTING state was entered again to reach this point, this signals to end this elevator thread
            log.debug("No remaining active requests for elevator, ending simulation thread.");
        });

        elevatorThread.start();
    }
}
