package com.quickwolf.web.service.impl;

import java.util.List;
import java.util.Optional;

import com.quickwolf.web.service.DriverService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.repository.TripRepository;
import com.quickwolf.web.service.TripService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private DriverService driverService;

	@Transactional(readOnly = true)
	@Override
	public List<Trip> findTripsBy(TripFormBean t) {
		List<Trip> trips = tripRepository.findTripsBy(t.getFromCountry(), t.getFromCity(), t.getToCountry(), t.getToCity(),
				t.getDepartTime());
		for (Trip trip : trips) {
			Hibernate.initialize(trip.getItinerary().getSteps());
		}
		return trips;
	}

	@Override
	public Trip createTrip(AddTripFormBean trip, String driverEmail) {
		Trip t = trip.toTrip();
		t.setDriver(driverService.findDriverBy(driverEmail));
		return tripRepository.save(t);
	}

	@Override
	public Trip findById(long tripId) {
		Optional<Trip> trip = tripRepository.findById(tripId);
		return trip.orElse(null);
	}

	@Override
	public void bookTrip(String email, long tripId) {

	}

	@Override
	public void cancelTrip(String email, long tripId) {

	}
}
