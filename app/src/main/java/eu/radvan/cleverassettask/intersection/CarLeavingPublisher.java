package eu.radvan.cleverassettask.intersection;

import eu.radvan.cleverassettask.event.CarMayLeaveEvent;
import eu.radvan.cleverassettask.model.CarLaneType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Slf4j
// beaned in IntersectionConfiguration
public class CarLeavingPublisher {

    // class fires events when a car may leave (`s1` interval)
    private final TaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;
    private final Duration tick;
    private final IntersectionContext context;

    public CarLeavingPublisher(Duration tick, TaskScheduler taskScheduler, ApplicationEventPublisher eventPublisher, IntersectionContext context) {
        this.taskScheduler = taskScheduler;
        this.tick = tick;
        this.eventPublisher = eventPublisher;
        this.context = context;
    }


    // I had to declare scheduling this way instead of @Scheduled because the annotation requires static string,
    // and it is not possible to provide a straightforward data to the annotation
    // since we create 4 different beans with the same impl but different configuration properties
    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void hintCarMayLeave() {
        taskScheduler.scheduleAtFixedRate( // @Scheduled alternative
                this::carMayLeave,
                tick
        );
    }

    private void carMayLeave() {
        List<CarLaneType> carLaneTypes = context.activeSemaphore().greenLanes();
        log.info("Publishing CarMayLeaveEvent for {}", Arrays.toString(carLaneTypes.stream().map(CarLaneType::getLabel).toArray()));
        carLaneTypes.forEach(carLane -> eventPublisher.publishEvent(new CarMayLeaveEvent(this, carLane)));
    }

}
