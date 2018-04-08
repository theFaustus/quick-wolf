package com.quickwolf.domain;

import java.util.Date;

public class ItineraryStep {
	private Long id;
	private String name;
	private Date arrive;
	private Date depart;
	private Address address;
	private Itinerary itinerary;

	public ItineraryStep() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getArrive() {
		return arrive;
	}

	public void setArrive(Date arrive) {
		this.arrive = arrive;
	}

	public Date getDepart() {
		return depart;
	}

	public void setDepart(Date depart) {
		this.depart = depart;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}
	
	public static ItineraryStepBuilder newBuilder() {
		return new ItineraryStepBuilder();
	}
	
	public static class ItineraryStepBuilder {
		private ItineraryStep step = new ItineraryStep();
		
		public ItineraryStepBuilder setName(String name) {
			step.name = name;
			return this;
		}
		
		public ItineraryStepBuilder setArrive(Date arrive) {
			step.arrive = arrive;
			return this;
		}
		
		public ItineraryStepBuilder setDepart(Date depart) {
			step.depart = depart;
			return this;
		}
		
		public ItineraryStepBuilder setAddress(Address address) {
			step.address = address;
			return this;
		}
		
		public ItineraryStepBuilder setItinerary(Itinerary itinerary) {
			step.itinerary = itinerary;
			return this;
		}
		
		public ItineraryStep build() {
			return step;
		}
	}
}
