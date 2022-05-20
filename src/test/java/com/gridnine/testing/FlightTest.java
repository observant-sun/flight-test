package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2007, 9, 1, 10, 0);

    private static final LocalDateTime[] DATE_TIMES = new LocalDateTime[] {
            DATE_TIME,
            DATE_TIME.plus(2, ChronoUnit.HOURS),

            DATE_TIME.plus(3, ChronoUnit.HOURS),
            DATE_TIME.plus(6, ChronoUnit.HOURS)
    };

    @Test
    void getSegments() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        LocalDateTime[] dateTimes = flight.getSegments().stream()
                .flatMap(s -> Stream.of(s.getDepartureDate(), s.getArrivalDate()))
                .toArray(LocalDateTime[]::new);
        assertArrayEquals(DATE_TIMES, dateTimes);
    }

    @Test
    void testToString1() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        assertEquals("[2007-09-01T10:00|2007-09-01T12:00] [2007-09-01T13:00|2007-09-01T16:00]",
                flight.toString());
    }

    @Test
    void testToString2() {
        Flight flight = new Flight(new ArrayList<>());
        assertEquals("", flight.toString());
    }

    @Test
    void getFirstDeparture() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        assertTrue(flight.getFirstDeparture().isPresent());
        assertEquals(DATE_TIME, flight.getFirstDeparture().get());
    }

    @Test
    void getFirstDeparture_EmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        assertFalse(flight.getFirstDeparture().isPresent());
    }

    @Test
    void getLastArrival() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        assertTrue(flight.getLastArrival().isPresent());
        assertEquals(DATE_TIME.plus(6, ChronoUnit.HOURS), flight.getLastArrival().get());
    }

    @Test
    void getLastArrival_EmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        assertFalse(flight.getLastArrival().isPresent());
    }
}
