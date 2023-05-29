package eu.radvan.cleverassettask.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record Car(CarLaneType type) {

}
