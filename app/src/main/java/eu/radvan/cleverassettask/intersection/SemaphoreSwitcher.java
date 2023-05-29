package eu.radvan.cleverassettask.intersection;

import eu.radvan.cleverassettask.event.SemaphoreGreenStartedEvent;
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
public class SemaphoreSwitcher {

    private final IntersectionContext context;
    private final TaskScheduler taskScheduler;

    private final Map<SemaphoreType,Semaphore> semaphores;

    public SemaphoreSwitcher(TaskScheduler taskScheduler, List<Semaphore> semaphores, IntersectionContext context) {
        this.taskScheduler = taskScheduler;
        this.context = context;
        this.semaphores = semaphores.stream().collect(Collectors.toMap(Semaphore::getType, Function.identity()));
    }


    // I had to declare scheduling this way instead of @Scheduled because the annotation requires static string,
    // and it is not possible to provide a straightforward data to the annotation
    // since we create 4 different beans with the same impl but different configuration properties
    @Async
    @EventListener
    public void prepareSwitchForNextSemaphore(SemaphoreGreenStartedEvent event) {
        Semaphore currentSemaphore = event.getSemaphore();
        Semaphore nextSemaphore = semaphores.get(currentSemaphore.nextState());
        // switch active semaphore after duration of current semaphore's green light
        log.debug("Scheduling next semaphore '{}' to start with a delay...", nextSemaphore);
        taskScheduler.schedule(() -> {
            log.debug("Next semaphore '{}' scheduled.", nextSemaphore);
            context.activeSemaphore(nextSemaphore);
        }, Instant.now().plus(currentSemaphore.tick()));
    }

}
