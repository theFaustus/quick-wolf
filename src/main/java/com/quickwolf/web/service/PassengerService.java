package com.quickwolf.web.service;

import java.util.List;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.form.beans.UpdatePasswordFormBean;
import com.quickwolf.web.form.beans.UpdateProfileFormBean;

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

    void updatePassword(String email, UpdatePasswordFormBean formBean);

    void updateProfile(String email, UpdateProfileFormBean formBean);
}
