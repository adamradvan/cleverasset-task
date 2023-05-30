package eu.radvan.cleverassettask.intersection.scheduling;

import eu.radvan.cleverassettask.event.SemaphoreGreenStartedEvent;
import eu.radvan.cleverassettask.intersection.Semaphore;
import eu.radvan.cleverassettask.intersection.ctx.IntersectionContext;
import eu.radvan.cleverassettask.model.SemaphoreType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
// beaned in IntersectionConfiguration
public class SemaphoreHandler {

    private final IntersectionContext context;
    private final TaskScheduler taskScheduler;

    private final Map<SemaphoreType, Semaphore> semaphores;

    public SemaphoreHandler(TaskScheduler taskScheduler, List<Semaphore> semaphores, IntersectionContext context) {
        this.taskScheduler = taskScheduler;
        this.context = context;
        this.semaphores = semaphores.stream().collect(Collectors.toMap(Semaphore::getType, Function.identity()));
    }

    @Async
    @EventListener
    public void prepareSwitchForNextSemaphore(SemaphoreGreenStartedEvent event) {
        Semaphore currentSemaphore = event.getSemaphore();
        Semaphore nextSemaphore = semaphores.get(currentSemaphore.nextState());
        // switch active semaphore after duration of current semaphore's green light
        log.debug("Scheduling next semaphore '{}' to start with a delay...", nextSemaphore);
        taskScheduler.schedule(() -> { // delayed invocation
            log.debug("Next semaphore '{}' scheduled.", nextSemaphore);
            context.activeSemaphore(nextSemaphore);
        }, Instant.now().plus(currentSemaphore.tick()));
    }

}
