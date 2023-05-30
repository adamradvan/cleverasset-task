package eu.radvan.cleverassettask.intersection.scheduling;


import eu.radvan.cleverassettask.event.CarMayLeaveEvent;
import eu.radvan.cleverassettask.intersection.CarLane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@Slf4j
// beaned in IntersectionConfiguration
public class CarLaneHandler {

    private final TaskScheduler taskScheduler;
    private final List<CarLane> carLanes;

    @Autowired
    public CarLaneHandler(TaskScheduler taskScheduler, List<CarLane> carLanes) {
        this.taskScheduler = taskScheduler;
        this.carLanes = carLanes;
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void addNewCarsToQueues() {
        carLanes.forEach(carLane -> taskScheduler.scheduleAtFixedRate( // @Scheduled alternative
                carLane::addCarsToTheQueue,
                carLane.getTick()
        ));
    }

    @Async
    @EventListener
    public void dequeueCar(CarMayLeaveEvent event) {
        carLanes.stream()
                .filter(e -> e.laneType() == event.getCarLaneType())
                .forEach(CarLane::dequeueCar);
    }

}
