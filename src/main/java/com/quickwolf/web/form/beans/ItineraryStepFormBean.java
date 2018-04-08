package com.quickwolf.web.form.beans;

import java.util.Date;

public class ItineraryStepFormBean {

	private String arrive;
	private String arriveTime;
	private String depart;
	private String departTime;
	private String name;
	private String country;
	private String city;
	private String street;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "ItineraryStepFormBean{" +
				"arrive='" + arrive + '\'' +
				", arriveTime='" + arriveTime + '\'' +
				", depart='" + depart + '\'' +
				", departTime='" + departTime + '\'' +
				", name='" + name + '\'' +
				", country='" + country + '\'' +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				'}';
	}
}
