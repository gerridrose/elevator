# Elevator
A homework coding challenge on simulating an elevator for my job application at Bluestaq.

## Assumptions
The assumptions that will be made on this simulation project.
- There is only one elevator in the simulation. -- Though multiple instances of elevator could be created.
- The amount of people walking into or exiting the elevator is untracked.
- The weight limit of the elevator is always within an acceptable range, therefore is untracked.
- Emergency buttons (ex. fire & alarm buttons) for the elevator will not exist.
- No basement floors at this time.
- The elevator algorithm will continue to move up until it reaches the top floor requested, then toggle
into a moving down state if there are floors below it with requests waiting.

## Features
- Configurable number of floors at startup.
- Configurable time to move between floors at startup.
- Configurable time to wait while elevator door is open on a floor at startup.
- Can press any number of floor buttons in any order at any given time in the elevator via REST (including OpenAPI/Swagger UI).
- Can press any external arrow buttons on any floor in any order at any given time to signal the elevator for pickup, 
and it will be queued appropriately based on the elevator algorithm.

## Features Not Implemented
- Change the number of floors runtime.
- Control the number of elevators at startup.
- Control the number of elevators at runtime.