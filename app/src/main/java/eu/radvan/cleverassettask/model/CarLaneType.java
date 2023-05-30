package eu.radvan.cleverassettask.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CarLaneType {
    NORTH_TO_SOUTH("N->S"),
    SOUTH_TO_NORTH("S->N"),
    WEST_TO_EAST  ("W->E"),
    EAST_TO_WEST  ("E->W");

    @Getter
    private final String label;

}
