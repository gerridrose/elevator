package com.bluestaq.elevator.dto;

import com.bluestaq.elevator.service.FloorRequest;

import java.io.Serializable;

public record FloorDto (int floorNumber, FloorRequest floorRequest) implements Serializable {}
