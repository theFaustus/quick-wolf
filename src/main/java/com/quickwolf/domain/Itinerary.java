package com.quickwolf.domain;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {
	private Long id;
	private List<ItineraryStep> steps = new ArrayList<>();
	private Trip trip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItineraryStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ItineraryStep> steps) {
		this.steps = steps;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Itinerary addItineraryStep(ItineraryStep step) {
		steps.add(step);
		return this;
	}
}
