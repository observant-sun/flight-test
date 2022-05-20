package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaxTotalTimeOnGroundFlightCriteriaTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2007, 9, 1, 10, 0);

    private static final LocalDateTime[] DATE_TIMES = new LocalDateTime[] {
            DATE_TIME, DATE_TIME.plus(2, ChronoUnit.HOURS),
            DATE_TIME.plus(3, ChronoUnit.HOURS), DATE_TIME.plus(6, ChronoUnit.HOURS),
            DATE_TIME.plus(9, ChronoUnit.HOURS), DATE_TIME.plus(12, ChronoUnit.HOURS)
    }; // (9-6)+(3-2) = 4 hours on ground

    @Test
    void testEmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        Duration duration = Duration.of(1, ChronoUnit.MINUTES);
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration).test(flight));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration, false).test(flight));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration, true).test(flight));
    }

    @Test
    void testTrue() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        Duration duration = Duration.of(4, ChronoUnit.HOURS).plus(Duration.of(1, ChronoUnit.MINUTES));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration).test(flight));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration, false).test(flight));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration, true).test(flight));
    }

    @Test
    void testFalse() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        Duration duration = Duration.of(3, ChronoUnit.HOURS).plus(Duration.of(59, ChronoUnit.MINUTES));
        assertFalse(new MaxTotalTimeOnGroundFlightCriteria(duration).test(flight));
        assertFalse(new MaxTotalTimeOnGroundFlightCriteria(duration, false).test(flight));
        assertFalse(new MaxTotalTimeOnGroundFlightCriteria(duration, true).test(flight));
    }

    @Test
    void testBorderline() {
        Flight flight = FlightBuilder.createFlight(DATE_TIMES);
        Duration duration = Duration.of(4, ChronoUnit.HOURS);
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration).test(flight));
        assertTrue(new MaxTotalTimeOnGroundFlightCriteria(duration, false).test(flight));
        assertFalse(new MaxTotalTimeOnGroundFlightCriteria(duration, true).test(flight));
    }
}
