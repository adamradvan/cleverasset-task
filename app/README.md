# Intersection App

### Configurable properties
The following entities feature at the intersection:
- 2 traffic lights, each light has a variable green light duration
  - each traffic light has its own identifier (see `SemaphoreType` enum)
- 4 car lanes, each lane has a variable number of cars arriving at variable intervals
  - each lane has its own identifier (see `CarLaneType` enum)

Variable values have been declared as configuration properties in `application.yml`
- It is configurable for which lanes the traffic light is green (`intersection-app.semaphores[].on-green`)
- It is configurable in which order the traffic lights follow each other
  - simulation of Finite Automata

### Design
- The app runs on SpringBoot, the essence of the app is that at given intervals events are processed such as the arrival of a car, green at the traffic light, departure of a car ...
- Components appearing in the app have been declared as beans in `IntersectionConfiguration`
  - theoretically and practically `CarLane` + `Semaphore` do not need to be beans, but I wanted to simplify my work at least with this Bean declaration by declaring them as singletons
- The app maintains an `IntersectionContext` context where basically only the active traffic light is switched (traffic light where it is green)
- Components that do the scheduling do so through `TaskScheduler` and not through `@Scheduled`
  - `CarLaneHandler`
    - adds and removes cars to/from the queue (each lane has its own car queue)
  - `CarMayLeavePublisher`
    - publishes events (`CarMayLeaveEvent`) at intervals indicating that a car may leave the queue
  - `SemaphoreHandler`
    - switches traffic lights in a way that whenever one traffic light starts to be green (`SemaphoreGreenStartedEvent`), 
      it schedules the next traffic light which will switch after `t1/t2` time, 
      depending on how long the current traffic light should be green

### Notes
The strength of the app is that things can be configured through `application.yml`, and with such configuration it's easy
to add new lanes, traffic lights or change the logic of traffic lights in terms of which lanes they are green for.

The process of cars leaving the intersection is simplified in the app.
Instead of launching an interval when a car can leave after the green light comes on,
I simplified it so that this interval is not tied to when the green light started.
Events are published at intervals indicating that a car may leave, and if it has a green light, it leaves.

I've added some basic logging of the application's progress, but it's not uniform
and could definitely be improved to better and more directly illustrate the situation at the intersection.

