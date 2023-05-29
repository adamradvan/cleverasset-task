package eu.radvan.cleverassettask.event;

import org.springframework.context.ApplicationEvent;

public class CarArrivesEvent extends ApplicationEvent {

    public CarArrivesEvent(Object source) {
        super(source);
    }
}
