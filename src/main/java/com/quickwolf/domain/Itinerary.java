package com.quickwolf.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_itinerary", schema = "wolf")
public class Itinerary extends AbstractEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "itinerary")
	private List<ItineraryStep> steps = new ArrayList<>();

	@OneToOne
	private Trip trip;

	public Itinerary() {
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
