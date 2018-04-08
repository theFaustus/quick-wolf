package com.quickwolf.domain;

import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
public class Driver {
    private long driverId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String humanId;
    private String driverEmail;
    private String driverPassword;
    private String telephoneNumber;
    private CreditCard creditCard;
    private Transport registeredTransport;
    private List<Trip> addedTrips;
    private int enabled = 1;

    private Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.humanId = builder.humanId;
        this.driverEmail = builder.email;
        this.driverPassword = builder.password;
        this.telephoneNumber = builder.telephoneNumber;
        this.creditCard = builder.creditCard;
        this.registeredTransport = builder.registeredTransport;
        this.addedTrips = builder.addedTrips;
        this.enabled = builder.enabled;
    }

    public static Builder newDriver() {
        return new Builder();
    }


    public static final class Builder {
        private long driverId;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String humanId;
        private String email;
        private String password;
        private String telephoneNumber;
        private CreditCard creditCard;
        private Transport registeredTransport;
        private int enabled;
        private List<Trip> addedTrips;

        private Builder() {
        }

        public Driver build() {
            return new Driver(this);
        }

        public Builder driverId(long driverId) {
            this.driverId = driverId;
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

        public Builder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder humanId(String humanId) {
            this.humanId = humanId;
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

        public Builder creditCards(CreditCard creditCard) {
            this.creditCard = creditCard;
            return this;
        }

        public Builder registeredTransport(Transport registeredTransport) {
            this.registeredTransport = registeredTransport;
            return this;
        }

        public Builder addedTrips(List<Trip> addedTrips) {
            this.addedTrips = addedTrips;
            return this;
        }

        public Builder enabled(int enabled) {
            this.enabled = enabled;
            return this;
        }
    }

    public List<Trip> getAddedTrips() {
        return addedTrips;
    }

    public void setAddedTrips(List<Trip> addedTrips) {
        this.addedTrips = addedTrips;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(String humanId) {
        this.humanId = humanId;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Transport getRegisteredTransport() {
        return registeredTransport;
    }

    public void setRegisteredTransport(Transport registeredTransport) {
        this.registeredTransport = registeredTransport;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", humanId='" + humanId + '\'' +
                ", driverEmail='" + driverEmail + '\'' +
                ", driverPassword='" + driverPassword + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", creditCard=" + creditCard +
                ", registeredTransport=" + registeredTransport +
                ", addedTrips=" + addedTrips +
                ", enabled=" + enabled +
                '}';
    }
}
