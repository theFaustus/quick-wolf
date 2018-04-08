package com.quickwolf.web.repository;

import com.quickwolf.domain.Passenger;

import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
public interface PassengerRepository {
    Passenger savePassenger(Passenger passenger);
    Passenger findPassengerBy(long id);
    Passenger findPassengerBy(String email);
    List<Passenger> findAllPassengers();
    void updateEnabledValue(String email, int value);
    void bookTrip(String passengerEmail, long tripId);
    void cancelTrip(String passengerEmail, long tripId);
}
