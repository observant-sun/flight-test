package com.gridnine.testing.criteria;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeparturesBeforeArrivalsFlightCriteriaTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2000, 1, 15, 10, 0);

    @Test
    void testNullSegmentList() {
        Flight flight = new Flight(null);
        assertThrows(NullPointerException.class, () -> new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertThrows(NullPointerException.class, () -> new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertThrows(NullPointerException.class, () -> new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }

    @Test
    void testEmptySegmentList() {
        Flight flight = new Flight(new ArrayList<>());
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }

    @Test
    void testTrue() {
        Segment s1 = new Segment(
                DATE_TIME,
                DATE_TIME.plus(2, ChronoUnit.HOURS));
        Segment s2 = new Segment(
                DATE_TIME.plus(4, ChronoUnit.HOURS),
                DATE_TIME.plus(6, ChronoUnit.HOURS));
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(s1);
        segmentList.add(s2);
        Flight flight = new Flight(segmentList);
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }

    @Test
    void testFalse() {
        Segment s1 = new Segment(
                DATE_TIME,
                DATE_TIME.plus(4, ChronoUnit.HOURS));
        Segment s2 = new Segment(
                DATE_TIME.plus(6, ChronoUnit.HOURS), // dep < arr
                DATE_TIME.plus(5, ChronoUnit.HOURS));
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(s1);
        segmentList.add(s2);
        Flight flight = new Flight(segmentList);
        assertFalse(new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertFalse(new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertFalse(new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }

    @Test
    void testDepEqArr() {
        Segment s1 = new Segment(
                DATE_TIME,
                DATE_TIME); // dep == arr
        Segment s2 = new Segment(
                DATE_TIME.plus(5, ChronoUnit.HOURS),
                DATE_TIME.plus(7, ChronoUnit.HOURS));
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(s1);
        segmentList.add(s2);
        Flight flight = new Flight(segmentList);
        assertFalse(new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertFalse(new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }

    // returns true, despite dep 2 being before arr 1 -- filter doesn't check it
    @Test
    void testNextDepartureBeforeLastArrival() {
        Segment s1 = new Segment(
                DATE_TIME,
                DATE_TIME.plus(2, ChronoUnit.HOURS)); // !
        Segment s2 = new Segment(
                DATE_TIME.plus(1, ChronoUnit.HOURS), // !
                DATE_TIME.plus(3, ChronoUnit.HOURS));
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(s1);
        segmentList.add(s2);
        Flight flight = new Flight(segmentList);
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria().test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(true).test(flight));
        assertTrue(new DeparturesBeforeArrivalsFlightCriteria(false).test(flight));
    }
}
