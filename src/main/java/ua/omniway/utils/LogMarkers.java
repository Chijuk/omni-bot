package ua.omniway.utils;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogMarkers {
    public static final Marker USER_ACTIVITY = MarkerFactory.getMarker("user_activity");
    public static final Marker OT_ACTIVITY = MarkerFactory.getMarker("ot_activity");
    public static final Marker PERFORMANCE = MarkerFactory.getMarker("performance");

    private LogMarkers() {
    }
}
