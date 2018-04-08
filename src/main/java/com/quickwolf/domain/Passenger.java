package com.quickwolf.domain;

import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
public class Passenger {
    private long passengerId;
    private String firstName;
    private String lastName;
    private String passengerEmail;
    private String passengerPassword;
    private String telephoneNumber;
    private List<Trip> bookedTrips;
    private CreditCard creditCard;
    private int enabled = 1;

    private Passenger(Builder builder) {
        this.passengerId = builder.passengerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.passengerEmail = builder.email;
        this.passengerPassword = builder.password;
        this.telephoneNumber = builder.telephoneNumber;
        this.bookedTrips = builder.bookedTrips;
        this.creditCard = builder.creditCard;
        this.enabled = builder.enabled;
    }



    public static Builder newPassenger() {
        return new Builder();
    }


    public static final class Builder {
        private long passengerId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String telephoneNumber;
        private List<Trip> bookedTrips;
        private CreditCard creditCard;
        private int enabled;

        private Builder() {
        }

        public Passenger build() {
            return new Passenger(this);
        }

        public Builder passengerId(long passengerId) {
            this.passengerId = passengerId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public Builder bookedTrips(List<Trip> bookedTrips) {
            this.bookedTrips = bookedTrips;
            return this;
        }

        public Builder creditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
            return this;
        }

        public Builder enabled(int enabled) {
            this.enabled = enabled;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passengerEmail='" + passengerEmail + '\'' +
                ", passengerPassword='" + passengerPassword + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", bookedTrips=" + bookedTrips +
                ", creditCard=" + creditCard +
                ", enabled=" + enabled +
                '}';
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerPassword() {
        return passengerPassword;
    }

    public void setPassengerPassword(String passengerPassword) {
        this.passengerPassword = passengerPassword;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public long getPassengerId() {
        return passengerId;
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

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
