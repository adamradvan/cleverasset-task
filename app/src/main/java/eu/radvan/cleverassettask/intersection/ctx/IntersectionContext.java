package eu.radvan.cleverassettask.intersection.ctx;

import eu.radvan.cleverassettask.event.SemaphoreGreenStartedEvent;
import eu.radvan.cleverassettask.intersection.Semaphore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
// beaned in IntersectionConfiguration
public class IntersectionContext {

    private final ApplicationEventPublisher eventPublisher;

    private final AtomicReference<Semaphore> activeSemaphore = new AtomicReference<>();

    public IntersectionContext(Semaphore initial, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.activeSemaphore.set(initial);
    }

    public void activeSemaphore(Semaphore nextSemaphore) {
        this.activeSemaphore.set(nextSemaphore);

        publishSemaphoreEvent(nextSemaphore);
    }

    public Semaphore activeSemaphore() {
        return this.activeSemaphore.get();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialSemaphore() {
        Semaphore semaphore = this.activeSemaphore.get();

        publishSemaphoreEvent(semaphore);
    }

    private void publishSemaphoreEvent(Semaphore semaphore) {
        log.info("Publishing event | Semaphore '{}' has a GREEN light.", semaphore);
        eventPublisher.publishEvent(new SemaphoreGreenStartedEvent(this, semaphore));
    }

}
