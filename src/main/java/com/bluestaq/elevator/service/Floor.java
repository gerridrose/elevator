package com.bluestaq.elevator.service;

/**
 * Holds elevator requests relating to a certain floor.
 */
public class Floor {
    // button pressed to go up at this floor outside elevator
    boolean arrowUp = false;
    // button pressed to go down at this floor outside elevator
    boolean arrowDown = false;
    // button pressed to stop at this floor inside elevator
    boolean requestedFloor = false;

    @Override
    public String toString() {
        return "Floor{" +
                "arrowUp=" + arrowUp +
                ", arrowDown=" + arrowDown +
                ", requestedFloor=" + requestedFloor +
                '}';
    }
}
