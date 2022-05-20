package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MaxTotalTimeOnGroundFlightCriteria implements FlightCriteria {

    private final Duration maxDuration;
    private final boolean exclusive;

    /**
     * Criteria for Flight that returns true if total time on ground (time between segments) doesn't
     * exceed the specified Duration.
     */
    public MaxTotalTimeOnGroundFlightCriteria(Duration maxDuration) {
        this(maxDuration, false);
    }

    /**
     * Criteria for Flight that returns true if total time on ground (total time between consequent segments) doesn't
     * exceed (or, if 'exclusive' is true, is less than) the specified Duration.
     * Doesn't validate that arrival of prev segment comes before departure of next segment.
     */
    public MaxTotalTimeOnGroundFlightCriteria(Duration maxDuration, boolean exclusive) {
        this.maxDuration = maxDuration;
        this.exclusive = exclusive;
    }

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();
        long sum = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            sum += segments.get(i).getArrivalDate().until(segments.get(i + 1).getDepartureDate(), ChronoUnit.SECONDS);
        }
        long maxSeconds = maxDuration.getSeconds();
        return exclusive ? sum < maxSeconds : sum <= maxSeconds;
    }

}
