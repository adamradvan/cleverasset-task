package eu.radvan.cleverassettask.intersection;

import eu.radvan.cleverassettask.model.CarLaneType;
import eu.radvan.cleverassettask.model.SemaphoreType;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
// beaned in IntersectionConfiguration
public class Semaphore {

    private final IntersectionProperties.SemaphoreProperties properties;

    public Semaphore(IntersectionProperties.SemaphoreProperties properties) {
        this.properties = properties;
    }

    public SemaphoreType getType() {
        return properties.type();
    }

    public SemaphoreType nextState() {
        return properties.nextState();
    }

    public Duration tick() {
        return properties.tick();
    }

    public List<CarLaneType> greenLanes() {
        return properties.onGreen();
    }

    @Override
    public String toString() {
        return properties.type().getLabel();
    }
}
