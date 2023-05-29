package eu.radvan.cleverassettask.event;

import eu.radvan.cleverassettask.intersection.Semaphore;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SemaphoreGreenStartedEvent extends ApplicationEvent {

    @Getter
    private final Semaphore semaphore;

    public SemaphoreGreenStartedEvent(Object source, Semaphore semaphore) {
        super(source);
        this.semaphore = semaphore;
    }
}
