package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DepartureAfterNowFlightCriteriaTest {

    @Test
    void testEmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        assertFalse(new DepartureAfterNowFlightCriteria().test(flight));
        assertFalse(new DepartureAfterNowFlightCriteria(false).test(flight));
        assertFalse(new DepartureAfterNowFlightCriteria(true).test(flight));
    }

    @Test
    void testTrue() {
        LocalDateTime now = LocalDateTime.now();
        Segment[] segments = {
                new Segment(
                        now.plus(1, ChronoUnit.HOURS),
                        now.plus(2, ChronoUnit.HOURS))
        };
        Flight flight = new Flight(Arrays.asList(segments));
        assertTrue(new DepartureAfterNowFlightCriteria().test(flight));
        assertTrue(new DepartureAfterNowFlightCriteria(true).test(flight));
        assertTrue(new DepartureAfterNowFlightCriteria(false).test(flight));
    }

    @Test
    void testFalse() {
        LocalDateTime now = LocalDateTime.now();
        Segment[] segments = {
                new Segment(
                        now.minus(1, ChronoUnit.HOURS),
                        now.plus(1, ChronoUnit.HOURS))
        };
        Flight flight = new Flight(Arrays.asList(segments));
        assertFalse(new DepartureAfterNowFlightCriteria().test(flight));
        assertFalse(new DepartureAfterNowFlightCriteria(true).test(flight));
        assertFalse(new DepartureAfterNowFlightCriteria( false).test(flight));
    }
}
