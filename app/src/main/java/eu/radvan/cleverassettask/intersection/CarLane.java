package eu.radvan.cleverassettask.intersection;

import eu.radvan.cleverassettask.model.Car;
import eu.radvan.cleverassettask.model.CarLaneType;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

@Slf4j
// beaned in IntersectionConfiguration
public class CarLane {

    private final Queue<Car> carQueue = new LinkedList<>();

    private final IntersectionProperties.CarLaneProperties properties;

    public CarLane(IntersectionProperties.CarLaneProperties properties) {
        this.properties = properties;
    }

    // scheduled manually in CarLaneHandler (instead of using @Scheduled)
    public void addCarsToTheQueue() {
        IntStream.range(0, this.properties.amount())
                .mapToObj(i -> new Car(this.properties.type()))
                .forEach(this.carQueue::add);

        log.info("{} | Generated cars, new queue length: {}", properties.type().getLabel(), carQueue.size());
    }


    public void dequeueCar() {
        carQueue.poll();

        log.info("{} | Removed car. Current queue: {}", properties.type().getLabel(), carQueue.size());
    }


    public Duration getTick() {
        return properties.tick();
    }

    public CarLaneType laneType() {
        return properties.type();
    }

}
