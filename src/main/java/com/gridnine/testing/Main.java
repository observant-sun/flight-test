package com.gridnine.testing;

import com.gridnine.testing.criteria.DepartureAfterNowFlightCriteria;
import com.gridnine.testing.criteria.DeparturesBeforeArrivalsFlightCriteria;
import com.gridnine.testing.criteria.FlightCriteria;
import com.gridnine.testing.criteria.MaxTotalTimeOnGroundFlightCriteria;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FlightCriteria[] flightCriteriaArray = {
                new DepartureAfterNowFlightCriteria(false),
                new DeparturesBeforeArrivalsFlightCriteria(false),
                new MaxTotalTimeOnGroundFlightCriteria(Duration.of(2, ChronoUnit.HOURS), false)
        };

        List<Flight> testList = FlightBuilder.createFlights();

        for (FlightCriteria fc : flightCriteriaArray) {
            testList.stream()
                    .filter(fc)
                    .forEach(System.out::println);
            System.out.println();
        }
    }

}
