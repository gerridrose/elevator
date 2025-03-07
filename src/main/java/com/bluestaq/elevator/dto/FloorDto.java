package com.bluestaq.elevator.dto;

import com.bluestaq.elevator.service.Floor;

import java.io.Serializable;

public record FloorDto (int floorNumber, Floor floor) implements Serializable {}
