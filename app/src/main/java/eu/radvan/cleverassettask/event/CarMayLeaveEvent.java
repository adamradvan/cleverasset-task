package eu.radvan.cleverassettask.event;

import eu.radvan.cleverassettask.model.CarLaneType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class CarMayLeaveEvent extends ApplicationEvent {

    @Getter
    private final CarLaneType carLaneType;

    public CarMayLeaveEvent(Object source, CarLaneType carLaneType) {
        super(source);
        this.carLaneType = carLaneType;
    }
}
