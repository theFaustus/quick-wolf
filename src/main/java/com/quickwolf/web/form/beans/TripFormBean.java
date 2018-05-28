package com.quickwolf.web.form.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TripFormBean {

	private String fromCountry;
	private String fromCity;
	private String toCountry;
	private String toCity;
	private String departTime;
	private String driverId;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public TripFormBean() {
	}

	private TripFormBean(final TripFormBean bean) {
		this.fromCity = bean.fromCity;
		this.fromCountry = bean.fromCountry;
		this.toCity = bean.toCity;
		this.toCountry = bean.toCountry;
		this.departTime = bean.departTime;
		this.driverId = bean.driverId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
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

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public Date getDepartDate() {
		try {
			return dateFormat.parse(getDepartTime());
		} catch (ParseException e) {
		}
		return null;
	}

	@Override
	public String toString() {
		return "TripFormBean{" +
				"fromCountry='" + fromCountry + '\'' +
				", fromCity='" + fromCity + '\'' +
				", toCountry='" + toCountry + '\'' +
				", toCity='" + toCity + '\'' +
				", departTime='" + departTime + '\'' +
				", driverId='" + driverId + '\'' +
				'}';
	}

	public TripFormBean trimAndLowercaseFields() {
		TripFormBean bean = new TripFormBean(this);
		bean.toCountry = StringUtils.trim(StringUtils.lowerCase(bean.toCountry));
		bean.fromCountry = StringUtils.trim(StringUtils.lowerCase(bean.fromCountry));
		bean.toCity = StringUtils.trim(StringUtils.lowerCase(bean.toCity));
		bean.fromCity = StringUtils.trim(StringUtils.lowerCase(bean.fromCity));
		return bean;
	}
}
