package com.quickwolf.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quickwolf.util.Constants;

/**
 * Created by Faust on 4/19/2017.
 */
@Entity
@Table(name = "_passenger", schema = "wolf")
public class Passenger extends User {

    @ManyToMany
    @JsonIgnore
    private List<Trip> bookedTrips = new ArrayList<>();

    public Passenger() {
    }

    public List<Trip> getBookedTrips() {
        return bookedTrips;
    }

    public void setBookedTrips(List<Trip> bookedTrips) {
        this.bookedTrips = bookedTrips;
    }

    public void addTrip(Trip trip) {
        bookedTrips.add(trip);
    }

    public static PassengerBuilder newBuilder() {
        return new PassengerBuilder();
    }

    public static final class PassengerBuilder {
        private Passenger passenger = new Passenger();

        private PassengerBuilder() {
        }

        public PassengerBuilder setEmail(String email) {
            passenger.setEmail(email);
            return this;
        }

        public PassengerBuilder setPassword(String password) {
            passenger.setPassword(password);
            return this;
        }

        public PassengerBuilder setRole(String role) {
            passenger.setRole(role);
            return this;
        }

        public PassengerBuilder setFirstName(String firstName) {
            passenger.setFirstName(firstName);
            return this;
        }

        public PassengerBuilder setEnabled(int enabled) {
            passenger.setEnabled(enabled);
            return this;
        }

        public PassengerBuilder setId(Long id) {
            passenger.setId(id);
            return this;
        }

        public PassengerBuilder setLastName(String lastName) {
            passenger.setLastName(lastName);
            return this;
        }

        public PassengerBuilder setTelephoneNumber(String telephoneNumber) {
            passenger.setTelephoneNumber(telephoneNumber);
            return this;
        }

        public PassengerBuilder setBookedTrips(List<Trip> bookedTrips) {
            passenger.bookedTrips = bookedTrips;
            return this;
        }

        public Passenger build() {
            passenger.setRole(Constants.DEFAULT_PASSENGER_ROLE);
            return passenger;
        }
    }
}
