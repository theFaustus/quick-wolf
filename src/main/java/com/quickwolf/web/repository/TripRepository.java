package com.quickwolf.web.repository;

import java.util.List;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.TripFormBean;

public interface TripRepository {

	List<Trip> findTripsBy(TripFormBean trip);
	List<Trip> findBookedTrips(String email);
	List<Trip> findAddedTrips(String email);
	Driver findDriverBy(String email);
	List<Driver> findAllDrivers();
	Trip findTripBy(long id);
	Trip createTrip(Trip t);

}
