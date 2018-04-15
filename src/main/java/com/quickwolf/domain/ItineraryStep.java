package com.quickwolf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "_itinerary_step", schema = "wolf")
public class ItineraryStep extends AbstractEntity {

	@Column(name = "step_name")
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrive_time")
	private Date arrive;

	@Column(name = "depart_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date depart;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "country", column = @Column(name = "istep_country")),
			@AttributeOverride(name = "state", column = @Column(name = "istep_state")),
			@AttributeOverride(name = "city", column = @Column(name = "istep_city")),
			@AttributeOverride(name = "street", column = @Column(name = "istep_street")),
			@AttributeOverride(name = "zipCode", column = @Column(name = "istep_zip_code"))
	})
	private Address address;

	@ManyToOne
	@JsonIgnore
	private Itinerary itinerary;

	public ItineraryStep() {
		super();
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
