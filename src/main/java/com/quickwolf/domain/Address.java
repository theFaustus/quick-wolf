package com.quickwolf.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String country;
	private String state;
	private String city;
	private String street;
	private String countryCode;

	@Column(name = "zip_code")
	private String zipCode;

	public Address() {
		super();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public static AddressBuilder newBuilder() {
		return new AddressBuilder();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return String.format("%s, %s", city, country);
	}

	public static class AddressBuilder {
		private Address address = new Address();

		public AddressBuilder setCountry(String country) {
			address.country = country;
			return this;
		}

		public AddressBuilder setCity(String city) {
			address.city = city;
			return this;
		}

		public AddressBuilder setState(String state) {
			address.state = state;
			return this;
		}

		public AddressBuilder setStreet(String street) {
			address.street = street;
			return this;
		}

		public AddressBuilder setZipCode(String zipCode) {
			address.zipCode = zipCode;
			return this;
		}


		public AddressBuilder setCountryCode(String countryCode) {
			address.countryCode = countryCode;
			return this;
		}

		public Address build() {
			return address;
		}
	}
}
