package ua.omniway.utils;

import org.springframework.util.StopWatch;

public class CustomStopWatch extends StopWatch {
    public CustomStopWatch(String id) {
        super(id);
    }

    @Override
    public String shortSummary() {
        return "StopWatch '" + getId() + "': running time = " + getTotalTimeSeconds() + " s";
    }
}
