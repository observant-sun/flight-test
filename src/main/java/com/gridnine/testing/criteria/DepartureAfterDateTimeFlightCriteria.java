package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;

import java.time.LocalDateTime;

public class DepartureAfterDateTimeFlightCriteria implements FlightCriteria {

    private final LocalDateTime dateTime;
    private final boolean exclusive;

    /**
     * Criteria for Flight that returns true for a Flight that has first departure date-time
     * after the specified date-time argument.
     */
    public DepartureAfterDateTimeFlightCriteria(LocalDateTime localDateTime) {
        this(localDateTime, true);
    }

    /**
     * Criteria for Flight that returns true for a Flight that has first departure date-time
     * after (or not before, if parameter 'exclusive' is false) the specified date-time argument.
     */
    public DepartureAfterDateTimeFlightCriteria(LocalDateTime localDateTime, boolean exclusive) {
        this.dateTime = localDateTime;
        this.exclusive = exclusive;
    }

    @Override
    public boolean test(Flight flight) {
        if (exclusive)
            return flight.getFirstDeparture().isPresent() && flight.getFirstDeparture().get().isAfter(dateTime);
        else
            return flight.getFirstDeparture().isPresent() && !flight.getFirstDeparture().get().isBefore(dateTime);
    }
}
