package com.quickwolf.web.service;

import java.util.List;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.form.beans.TripFormBean;

public interface TripService {
	List<Trip> findTripsBy(TripFormBean trip);
	Trip createTrip(AddTripFormBean trip);

}
