package com.quickwolf.web.form.beans;

import java.io.Serializable;

public class BookTripFormBean implements Serializable {
	private long tripId;
	private String passengerEmail;

	public BookTripFormBean(String passengerEmail, long tripId) {
		this.tripId = tripId;
		this.passengerEmail = passengerEmail;
	}
	
	public BookTripFormBean() {
	}

	public String getPassengerEmail() {
		return passengerEmail;
	}

	public void setPassengerEmail(String passengerEmail) {
		this.passengerEmail = passengerEmail;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

}
