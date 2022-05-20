package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DepartureAfterDateTimeFlightCriteriaTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2007, 9, 1, 10, 0);

    @Test
    void testEmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME).test(flight));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, false).test(flight));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, true).test(flight));
    }

    @Test
    void testNullSegmentList() {
        Flight flight = new Flight(null);
        assertThrows(NullPointerException.class, () -> new DepartureAfterDateTimeFlightCriteria(DATE_TIME).test(flight));
        assertThrows(NullPointerException.class, () -> new DepartureAfterDateTimeFlightCriteria(DATE_TIME, false).test(flight));
        assertThrows(NullPointerException.class, () -> new DepartureAfterDateTimeFlightCriteria(DATE_TIME, true).test(flight));
    }

    @Test
    void testTrue() {
        Segment[] segments = {
                new Segment(
                        DATE_TIME.plus(1, ChronoUnit.MINUTES),
                        DATE_TIME.plus(2, ChronoUnit.HOURS))
        };
        Flight flight = new Flight(Arrays.asList(segments));
        assertTrue(new DepartureAfterDateTimeFlightCriteria(DATE_TIME).test(flight));
        assertTrue(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, true).test(flight));
        assertTrue(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, false).test(flight));
    }

    @Test
    void testFalse() {
        Segment[] segments = {
                new Segment(
                        DATE_TIME.minus(1, ChronoUnit.MINUTES),
                        DATE_TIME.plus(1, ChronoUnit.HOURS))
        };
        Flight flight = new Flight(Arrays.asList(segments));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME).test(flight));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, true).test(flight));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, false).test(flight));
    }

    @Test
    void testDepartureEqDateTime() {
        Segment[] segments = {
                new Segment(
                        DATE_TIME,
                        DATE_TIME.plus(1, ChronoUnit.HOURS))
        };
        Flight flight = new Flight(Arrays.asList(segments));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME).test(flight));
        assertFalse(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, true).test(flight));
        assertTrue(new DepartureAfterDateTimeFlightCriteria(DATE_TIME, false).test(flight));
    }

}
