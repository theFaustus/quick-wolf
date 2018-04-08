package com.quickwolf.web.form.beans;

import java.io.Serializable;

public class BookTripFormBean implements Serializable {
	private long tripId;
	private String email;

	public BookTripFormBean(String email, long tripId) {
		this.tripId = tripId;
		this.email = email;
	}
	
	public BookTripFormBean() {
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

}
