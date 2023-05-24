package eu.radvan.cleverassettask.intersection;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "intersection-app")
public class IntersectionProperties {

    private LeavingProperties leaving;
    private List<SemaphoreProperties> semaphores = new ArrayList<>();
    private List<CarProperties> cars = new ArrayList<>();

    /**
     * Property describes cars leaving the intersection
     */
    @Data
    public static class LeavingProperties {
        /**
         * Every {@code tick} one car leaves an intersection (if it has a green light)
         */
        private Duration tick;
    }

    /**
     * Property describes semaphores in the intersection
     */
    @Data
    public static class SemaphoreProperties {
        private SemaphoreType type;
        /**
         * Every {@code tick} the semaphore changes red->green / green->red
         */
        private Duration tick;
    }

    /**
     * Property describes cars in the intersection
     */
    @Data
    public static class CarProperties {
        /**
         * What type of car arrives to the intersection (its lane and direction)
         */
        private CarType type;
        /**
         * Cars arrives to the intersection every {@code interval} (e.g. every minute)
         */
        private Duration tick;

        /**
         * How many cars arrive to the intersection
         */
        private Integer amount;
    }

    public enum SemaphoreType {
        NORTH_SOUTH {
            @Override
            public SemaphoreType next() {
                return WEST_EAST;
            }
        },
        WEST_EAST {
            @Override
            public SemaphoreType next() {
                return NORTH_SOUTH;
            }
        };

        /**
         * Next semaphore type in the automaton
         *
         * @return semaphore type that comes after current one.
         */
        public abstract SemaphoreType next();
    }

    public enum CarType {
        NORTH_TO_SOUTH,
        SOUTH_TO_NORTH,
        WEST_TO_EAST,
        EAST_TO_WEST
    }

}
