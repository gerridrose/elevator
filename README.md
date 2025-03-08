# Elevator
A homework coding challenge on simulating an elevator for my job application at Bluestaq.

## Assumptions
The assumptions that will be made on this simulation project.
- There is only one elevator.
- The amount of people walking into or exiting the elevator is untracked.
- The weight limit of the elevator is always within an acceptable range, therefore is untracked.
- Emergency buttons (ex. fire & alarm buttons) for the elevator will not exist.
- No basement floors.

## Features
- Configurable number of floors at startup.
- Configurable time to move between floors at startup.
- Configurable time to wait while elevator door is open on a floor at startup.
- Can press any number of floor buttons in any order at any given time in the elevator via REST (including OpenAPI/Swagger UI).
- Can press any external arrow buttons on any floor in any order at any given time to signal the elevator for pickup, 
and it will be queued appropriately based on the elevator algorithm.
- The elevator algorithm, when in a running state, will continue to move up until it reaches the top floor requested, then toggle
into a moving down state if there are floors below it with requests waiting.

## Features Not Implemented
- Change the number of floors runtime.
- Control the number of elevators at startup or runtime.
- Change the elevator algorithm at startup or runtime (ex. move to nearest request pickup floor?).

## Development Tools
This project was built with the following tools & framework:
- Java 21
- Spring Boot 3.4.3
- Maven 3.9.9
- Intellij IDEA 2024.3.4.1

## How To Build, Run & Test
Firstly, this implementation uses REST to simulate user input.  An OpenAPI/Swagger
interface is included in the project so that when it runs you can easily view and query
these endpoints.

The below steps assumes you have already configured your machine with the above-mentioned Development Tools. 

1. **git clone** the project to a directory of your choice.
2. Change directory with your favorite terminal to the root directory of the project.
3. **mvn clean package**
4. **java -jar target/elevator-{version}.jar** - this will use the default configuration properties
5. Observe the console startup success noting the amount of elevator floors and the starting floor.
6. Open your favorite browser to **http://localhost:8080/elevator/swagger-ui/index.html#/**
7. Review the endpoints and test them as you wish, reviewing the console log at the same time.
8. That is all, hope you like!