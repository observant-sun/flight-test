package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DepartureAfterNowFlightCriteria implements FlightCriteria {

    private final boolean exclusive;

    /**
     * Criteria for Flight that returns true for a Flight that has first departure date-time
     * after current date-time, which is truncated to minutes.
     */
    public DepartureAfterNowFlightCriteria() {
        this(true);
    }

    /**
     * Criteria for Flight that returns true for a Flight that has first departure date-time
     * after (or not before, if parameter 'exclusive' is false) the current date-time, which is truncated to minutes.
     */
    public DepartureAfterNowFlightCriteria(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public boolean test(Flight flight) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (exclusive)
            return flight.getFirstDeparture().isPresent() && flight.getFirstDeparture().get().isAfter(now);
        else
            return flight.getFirstDeparture().isPresent() && !flight.getFirstDeparture().get().isBefore(now);
    }
}

