package com.quickwolf.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.repository.TripRepository;
import com.quickwolf.web.service.TripService;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository repository;
	
	@Override
	public List<Trip> findTripsBy(TripFormBean trip) {
		return repository.findTripsBy(trip);
	}

	@Override
	public Trip createTrip(AddTripFormBean trip) {
		Trip t = trip.toTrip();
		return repository.createTrip(t);
	}
}
