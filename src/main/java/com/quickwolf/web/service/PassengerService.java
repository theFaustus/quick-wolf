package com.quickwolf.web.service;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;

import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
public interface PassengerService {
    Passenger savePassenger(RegisterPassengerFormBean registerPassengerFormBean);

    Passenger findPassengerBy(String name);

    List<Trip> findBookedTrips(String name);

    List<Passenger> findAllPassengers();

    void updateEnabledValue(String email, int enabledPassenger);

    Passenger findPassengerByEmailWithFetchedTrips(String email);

    List<Passenger> findAllPassengersWithFetchedBookedTrips();
}
