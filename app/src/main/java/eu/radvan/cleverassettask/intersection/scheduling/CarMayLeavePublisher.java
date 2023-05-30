package eu.radvan.cleverassettask.intersection.scheduling;

import eu.radvan.cleverassettask.event.CarMayLeaveEvent;
import eu.radvan.cleverassettask.intersection.ctx.IntersectionContext;
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
public class CarMayLeavePublisher {

    private final TaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;
    private final Duration tickBetweenTwoCars;
    private final IntersectionContext context;

    public CarMayLeavePublisher(Duration tick, TaskScheduler taskScheduler, ApplicationEventPublisher eventPublisher, IntersectionContext context) {
        this.taskScheduler = taskScheduler;
        this.tickBetweenTwoCars = tick;
        this.eventPublisher = eventPublisher;
        this.context = context;
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void hintCarMayLeave() {
        taskScheduler.scheduleAtFixedRate( // @Scheduled alternative
                this::carMayLeave,
                tickBetweenTwoCars
        );
    }

    private void carMayLeave() {
        List<CarLaneType> carLaneTypes = context.activeSemaphore().greenLanes();
        log.info("Publishing CarMayLeaveEvent for {}", Arrays.toString(carLaneTypes.stream().map(CarLaneType::getLabel).toArray()));
        carLaneTypes.forEach(carLane -> eventPublisher.publishEvent(new CarMayLeaveEvent(this, carLane)));
    }

}
