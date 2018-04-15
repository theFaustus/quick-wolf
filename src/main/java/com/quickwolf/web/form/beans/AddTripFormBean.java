package com.quickwolf.web.form.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.quickwolf.domain.*;

public class AddTripFormBean {

	private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy HH:mm");
		}
	};

	private String depart;
	private String departTime;
	private String arrive;
	private String arriveTime;
	private String fromCountry;
	private String fromCity;
	private String fromStreet;
	private String toCountry;
	private String toCity;
	private String toStreet;
	private long driverId;
	private String email;
	private int availableSeats;
	private List<ItineraryStepFormBean> itinerarySteps = new ArrayList<>();
	private BigDecimal price = BigDecimal.TEN;

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromStreet() {
		return fromStreet;
	}

	public void setFromStreet(String fromStreet) {
		this.fromStreet = fromStreet;
	}

	public String getToCountry() {
		return toCountry;
	}

	public void setToCountry(String toCountry) {
		this.toCountry = toCountry;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToStreet() {
		return toStreet;
	}

	public void setToStreet(String toStreet) {
		this.toStreet = toStreet;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public List<ItineraryStepFormBean> getItinerarySteps() {
		return itinerarySteps;
	}

	public void setItinerarySteps(List<ItineraryStepFormBean> itinerarySteps) {
		this.itinerarySteps = itinerarySteps;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Trip toTrip() {
		try {
			return tryConvertToTrip();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private Trip tryConvertToTrip() throws ParseException {
		Trip trip = Trip.newBuilder()
				.setDepartTime(dateFormat.get().parse(depart + " " + departTime))
				.setDepartDate(dateFormat.get().parse(depart + " " + departTime))
				.setArriveTime(dateFormat.get().parse(arrive + " " + arriveTime))
				.setArriveDate(dateFormat.get().parse(arrive + " " + arriveTime))
				.setFromAddress(Address.newBuilder()
						.setCountry(fromCountry)
						.setCity(fromCity)
						.setStreet(fromStreet)
						.build())
				.setDestinationAddress(
						Address.newBuilder()
						.setCountry(toCountry)
						.setCity(toCity)
						.setStreet(toStreet)
						.build())
				.setItinerary(getItinerary())
				.setPrice(price)
				.setAvailableSeats(availableSeats)
				.setDriver(Driver.newBuilder().setEmail(email).build())
				.build();
		trip.getItinerary().setTrip(trip);
		return trip;
	}

	private Itinerary getItinerary() throws ParseException{
		Itinerary itinerary = new Itinerary();
		for (ItineraryStepFormBean step : itinerarySteps) {
			itinerary.addItineraryStep(ItineraryStep.newBuilder()
					.setDepart(dateFormat.get().parse(step.getDepart() + " " + step.getDepartTime()))
					.setArrive(dateFormat.get().parse(step.getArrive() + " " + step.getArriveTime()))
					.setName(step.getName())
					.setAddress(Address.newBuilder()
							.setCountry(step.getCountry())
							.setCity(step.getCity())
							.setStreet(step.getStreet())
							.build())
					.setItinerary(itinerary)
					.build());
		}
		return itinerary;
	}
}
