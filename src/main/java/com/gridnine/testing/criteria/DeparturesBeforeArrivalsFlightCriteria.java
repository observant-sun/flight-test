package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;

public class DeparturesBeforeArrivalsFlightCriteria implements FlightCriteria {

    private final boolean exclusive;

    /**
     * Criteria for Flight that returns true if in every Segment departure date-time comes
     * before arrival date-time.
     */
    public DeparturesBeforeArrivalsFlightCriteria() {
        this(true);
    }

    /**
     * Criteria for Flight that returns true if in every Segment departure date-time comes
     * before (or not after, if 'exclusive' is false) arrival date-time.
     */
    public DeparturesBeforeArrivalsFlightCriteria(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public boolean test(Flight flight) {
        if (exclusive)
            return flight.getSegments().stream()
                .allMatch(s -> s.getDepartureDate().isBefore(s.getArrivalDate()));
        else
            return flight.getSegments().stream()
                .noneMatch(s -> s.getDepartureDate().isAfter(s.getArrivalDate()));
    }

}
