package com.bluestaq.elevator.service;

import java.io.Serializable;

/**
 * Holds elevator requests relating to a certain floor.
 */
public class Floor implements Serializable {
    // button pressed to go up at this floor outside elevator
    public boolean arrowUp = false;
    // button pressed to go down at this floor outside elevator
    public boolean arrowDown = false;
    // button pressed to stop at this floor inside elevator
    public boolean requestedFloor = false;

    @Override
    public String toString() {
        return "Floor{" +
                "arrowUp=" + arrowUp +
                ", arrowDown=" + arrowDown +
                ", requestedFloor=" + requestedFloor +
                '}';
    }
}
