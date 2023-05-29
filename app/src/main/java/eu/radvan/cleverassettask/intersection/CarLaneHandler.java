package eu.radvan.cleverassettask.intersection;


import eu.radvan.cleverassettask.event.CarMayLeaveEvent;
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

    // I had to declare scheduling this way instead of @Scheduled because the annotation requires static string,
    // and it is not possible to provide a straightforward data to the annotation
    // since we create 4 different beans with the same impl but different configuration properties
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
