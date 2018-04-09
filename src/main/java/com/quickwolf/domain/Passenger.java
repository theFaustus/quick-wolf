package com.quickwolf.domain;

import com.quickwolf.util.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
@Entity
@Table(name = "_passenger", schema = "wolf")
public class Passenger extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @ManyToMany
    private List<Trip> bookedTrips = new ArrayList<>();

    @Embedded
    private CreditCard creditCard;

    public Passenger() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public List<Trip> getBookedTrips() {
        return bookedTrips;
    }

    public void setBookedTrips(List<Trip> bookedTrips) {
        this.bookedTrips = bookedTrips;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void addTrip(Trip trip) {
        bookedTrips.add(trip);
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static PassengerBuilder newBuilder() {
        return new PassengerBuilder();
    }

    public static final class PassengerBuilder {
        private String email;
        private String password;
        private String role;
        private String firstName;
        private int enabled = 1;
        private Long id;
        private String lastName;
        private String telephoneNumber;
        private List<Trip> bookedTrips = new ArrayList<>();

        private CreditCard creditCard;

        private PassengerBuilder() {
        }

        public PassengerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public PassengerBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public PassengerBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public PassengerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PassengerBuilder setEnabled(int enabled) {
            this.enabled = enabled;
            return this;
        }

        public PassengerBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public PassengerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PassengerBuilder setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public PassengerBuilder setBookedTrips(List<Trip> bookedTrips) {
            this.bookedTrips = bookedTrips;
            return this;
        }

        public PassengerBuilder setCreditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
            return this;
        }

        public Passenger build() {
            Passenger passenger = new Passenger();
            passenger.setEmail(email);
            passenger.setPassword(password);
            passenger.setRole(role);
            passenger.setFirstName(firstName);
            passenger.setEnabled(enabled);
            passenger.setId(id);
            passenger.setLastName(lastName);
            passenger.setTelephoneNumber(telephoneNumber);
            passenger.setBookedTrips(bookedTrips);
            passenger.setCreditCard(creditCard);
            passenger.setRole(Constants.DEFAULT_PASSENGER_ROLE);
            return passenger;
        }
    }
}
