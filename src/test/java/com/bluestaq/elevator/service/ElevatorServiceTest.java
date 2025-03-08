package com.bluestaq.elevator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class ElevatorServiceTest {

    @InjectMocks
    ElevatorService elevatorService;

    @Mock
    ElevatorContext elevatorContext;

    private final int testNumFloors = 20;
    ArrayList<FloorRequest> floorRequests = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // we are not spinning up a spring context, need to set @Value this way
        // typically more complex systems @ConfigurationProperties would be used
        // and that allows easier/cleaner mocking
        ReflectionTestUtils.setField(elevatorService, "numFloors", testNumFloors);
    }

    @Test
    void pressArrowUpOnFloor_RestingState_PressArrowUpOnFloor() {
        for (int i = 0; i < testNumFloors; i++) {
            floorRequests.add(new FloorRequest());
        }

        int pressedFloorNumber = 9;
        RestingState mockRestingState = Mockito.mock(RestingState.class);
        Mockito.doReturn(floorRequests).when(elevatorContext).getFloorRequests();
        Mockito.doReturn(mockRestingState).when(elevatorContext).getElevatorStateBase();
        Mockito.doNothing().when(mockRestingState).pressArrowUpOnFloor(pressedFloorNumber);

        elevatorService.pressArrowUpOnFloor(pressedFloorNumber);
        Mockito.verify(mockRestingState, Mockito.times(1)).pressArrowUpOnFloor(pressedFloorNumber);
    }

    @Test
    void pressArrowDownOnFloor_RestingState_pressArrowDownOnFloor() {
        for (int i = 0; i < testNumFloors; i++) {
            floorRequests.add(new FloorRequest());
        }

        int pressedFloorNumber = 13;
        RestingState mockRestingState = Mockito.mock(RestingState.class);
        Mockito.doReturn(floorRequests).when(elevatorContext).getFloorRequests();
        Mockito.doReturn(mockRestingState).when(elevatorContext).getElevatorStateBase();
        Mockito.doNothing().when(mockRestingState).pressArrowDownOnFloor(pressedFloorNumber);

        elevatorService.pressArrowDownOnFloor(pressedFloorNumber);
        Mockito.verify(mockRestingState, Mockito.times(1)).pressArrowDownOnFloor(pressedFloorNumber);
    }

    @Test
    void pressFloorNumber_RestingState_PressFloorNumber() {
        for (int i = 0; i < testNumFloors; i++) {
            floorRequests.add(new FloorRequest());
        }

        int pressedFloorNumber = 5;
        RestingState mockRestingState = Mockito.mock(RestingState.class);
        Mockito.doReturn(floorRequests).when(elevatorContext).getFloorRequests();
        Mockito.doReturn(mockRestingState).when(elevatorContext).getElevatorStateBase();
        Mockito.doNothing().when(mockRestingState).pressFloorNumber(pressedFloorNumber);

        elevatorService.pressFloorNumber(pressedFloorNumber);
        Mockito.verify(mockRestingState, Mockito.times(1)).pressFloorNumber(pressedFloorNumber);
    }

    @Test
    void pressCloseDoor_RestingState_IllegalArgumentException() {
        RestingState mockRestingState = Mockito.mock(RestingState.class);
        Mockito.doReturn(mockRestingState).when(elevatorContext).getElevatorStateBase();
        Mockito.doThrow(IllegalArgumentException.class).when(mockRestingState).pressCloseDoor();

        Assertions.assertThatThrownBy(() -> {
            elevatorService.pressCloseDoor();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pressOpenDoor_RestingState_StartElevatorDoorOpenState() {
        RestingState mockRestingState = Mockito.mock(RestingState.class);
        Mockito.doReturn(mockRestingState).when(elevatorContext).getElevatorStateBase();
        Mockito.doNothing().when(mockRestingState).pressOpenDoor();

        elevatorService.pressOpenDoor();
        Mockito.verify(mockRestingState, Mockito.times(1)).pressOpenDoor();
    }

    @Test
    void validateFloorNumber_ValidFloorNumbers_NoException() {
        int testFloorNumber = 7;

        Assertions.assertThatNoException().isThrownBy(() -> {
            elevatorService.validateFloorNumber(testFloorNumber);
        });

        // test edge cases as well
        int bottomFloorNumber = 1;
        Assertions.assertThatNoException().isThrownBy(() -> {
            elevatorService.validateFloorNumber(bottomFloorNumber);
        });

        int topFloorNumber = 20;
        Assertions.assertThatNoException().isThrownBy(() -> {
            elevatorService.validateFloorNumber(topFloorNumber);
        });
    }

    @Test
    void validateFloorNumber_InvalidFloorNumbers_IllegalArgumentException() {
        // test a couple difference invalid floor numbers
        int negativeFloorNumber = -8;

        Assertions.assertThatThrownBy(() -> {
            elevatorService.validateFloorNumber(negativeFloorNumber);
        }).isInstanceOf(IllegalArgumentException.class);

        int tooHighFloorNumber = 21;

        Assertions.assertThatThrownBy(() -> {
            elevatorService.validateFloorNumber(tooHighFloorNumber);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}