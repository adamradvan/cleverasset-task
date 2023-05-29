package eu.radvan.cleverassettask.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SemaphoreType {
    NORTH_SOUTH("N-S"),
    WEST_EAST  ("W-E");

    @Getter
    private final String label;
}
