package com.bluestaq.elevator.service;

/**
 * Interface to control an Elevator.
 */
public interface IElevatorControl {
    /**
     * Simulates pressing the up button on the corresponding floor external to the elevator.
     * @param floorNumber - The floor of interest.
     */
    public void pressArrowUpOnFloor(int floorNumber);
    /**
     * Simulates pressing the down button on the corresponding floor external to the elevator.
     * @param floorNumber - The floor of interest.
     */
    public void pressArrowDownOnFloor(int floorNumber);

    /**
     * Simulates pressing a floor number in the elevator.
     */
    public void pressFloorNumber(int floorNumber);

    /**
     * Simulates pressing the close door button.
     */
    public void pressCloseDoor();

    /**
     * Simulates pressing the open door button, resetting the door open time remaining.
     */
    public void pressOpenDoor();
}
