package eu.radvan.cleverassettask.intersection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class IntersectionConfiguration {
    private final IntersectionProperties properties;

    @Autowired
    public IntersectionConfiguration(IntersectionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        return scheduler;
    }

    @Bean
    public List<Semaphore> semaphores() {
        List<Semaphore> semaphores = properties.semaphores
                .stream()
                .map(Semaphore::new)
                .collect(Collectors.toList());

        if (semaphores.size() < 2) throw new IllegalStateException("Expecting at least 2 semaphores to be configured.");

        return semaphores;
    }

    @Bean
    public List<CarLane> carLanes() {
        List<CarLane> carLanes = properties.carLanes
                .stream()
                .map(CarLane::new)
                .collect(Collectors.toList());

        if (carLanes.size() < 4) throw new IllegalStateException("Expecting at least 4 car lanes to be configured.");

        return carLanes;
    }


    @Bean
    public IntersectionContext intersectionContext(ApplicationEventPublisher eventPublisher, List<Semaphore> semaphores) {
        return new IntersectionContext(semaphores.get(0), eventPublisher);
    }

    @Bean
    public SemaphoreSwitcher semaphoreSwitcher(TaskScheduler taskScheduler, List<Semaphore> semaphores, IntersectionContext context) {
        return new SemaphoreSwitcher(taskScheduler, semaphores, context);
    }

    @Bean
    public CarLaneHandler carLaneHandler(TaskScheduler taskScheduler, List<CarLane> carLanes) {
        return new CarLaneHandler(taskScheduler, carLanes);
    }

    @Bean
    public CarLeavingPublisher carSemaphoreWatcher(ApplicationEventPublisher eventPublisher, TaskScheduler taskScheduler, IntersectionContext context) {
        return new CarLeavingPublisher(properties.leaving.tick(), taskScheduler, eventPublisher, context);
    }
}
