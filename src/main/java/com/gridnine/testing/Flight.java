package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
public class Flight {
    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
            .collect(Collectors.joining(" "));
    }

    public Optional<LocalDateTime> getFirstDeparture() {
        return segments.size() > 0 ? Optional.of(segments.get(0).getDepartureDate()) : Optional.empty();
    }

    public Optional<LocalDateTime> getLastArrival() {
        return segments.size() > 0 ? Optional.of(segments.get(segments.size()-1).getArrivalDate()) : Optional.empty();
    }
}
