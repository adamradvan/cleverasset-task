package eu.radvan.cleverassettask.intersection;

import eu.radvan.cleverassettask.model.CarLaneType;
import eu.radvan.cleverassettask.model.SemaphoreType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "intersection-app")
public class IntersectionProperties {

    public LeavingProperties leaving;
    public List<SemaphoreProperties> semaphores = new ArrayList<>();
    public List<CarLaneProperties> carLanes = new ArrayList<>();


    /**
     * Property describes cars leaving the intersection
     *
     * @param tick Every {@code tick} one car leaves an intersection (if it has a green light)
     */
    public record LeavingProperties(
            Duration tick
    ) {}

    /**
     * Property describes semaphores in the intersection
     *
     * @param tick Every {@code tick} the semaphore changes red->green / green->red
     */
    public record SemaphoreProperties(
            SemaphoreType type,
            SemaphoreType nextState,
            List<CarLaneType> onGreen, // on green these car lanes are allowed to go
            Duration tick
    ) {}

    /**
     * Property describes cars in the intersection
     *
     * @param tick   Cars arrives to the intersection every {@code interval} (e.g. every minute)
     * @param amount How many cars arrive to the intersection
     */
    public record CarLaneProperties(
            CarLaneType type,
            Duration tick,
            Integer amount
    ) {}

}
