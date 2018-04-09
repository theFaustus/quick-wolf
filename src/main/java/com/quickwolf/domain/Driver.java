package com.quickwolf.domain;

import com.quickwolf.util.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
@Entity
@Table(name = "_driver", schema = "wolf")
public class Driver extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "driver_idnp")
    private String idnp;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Embedded
    private CreditCard creditCard;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Transport transport;

    @OneToMany(mappedBy = "driver")
    private List<Trip> addedTrips = new ArrayList<>();

    public Driver() {
        setRole(Constants.DEFAULT_DRIVER_ROLE);
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

    public String getIdnp() {
        return idnp;
    }

    public void setIdnp(String idnp) {
        this.idnp = idnp;
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

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public List<Trip> getAddedTrips() {
        return addedTrips;
    }

    public void setAddedTrips(List<Trip> addedTrips) {
        this.addedTrips = addedTrips;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", idnp='" + idnp + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }

    public static DriverBuilder newBuilder() {
        return new DriverBuilder();
    }

    public static final class DriverBuilder {
        private Driver driver = new Driver();

        private DriverBuilder() {
        }

        public DriverBuilder setEmail(String email) {
            driver.setEmail(email);
            return this;
        }

        public DriverBuilder setPassword(String password) {
            driver.setPassword(password);
            return this;
        }

        public DriverBuilder setRole(String role) {
            driver.setRole(role);
            return this;
        }

        public DriverBuilder setFirstName(String firstName) {
            driver.firstName = firstName;
            return this;
        }

        public DriverBuilder setEnabled(int enabled) {
            driver.setEnabled(enabled);
            return this;
        }

        public DriverBuilder setId(Long id) {
            driver.setId(id);
            return this;
        }

        public DriverBuilder setLastName(String lastName) {
            driver.lastName = lastName;
            return this;
        }

        public DriverBuilder setDateOfBirth(String dateOfBirth) {
            driver.dateOfBirth = dateOfBirth;
            return this;
        }

        public DriverBuilder setIdnp(String idnp) {
            driver.idnp = idnp;
            return this;
        }

        public DriverBuilder setTelephoneNumber(String telephoneNumber) {
            driver.telephoneNumber = telephoneNumber;
            return this;
        }

        public DriverBuilder setCreditCard(CreditCard creditCard) {
            driver.creditCard = creditCard;
            return this;
        }

        public DriverBuilder setTransport(Transport transport) {
            driver.transport = transport;
            transport.setDriver(driver);
            return this;
        }

        public DriverBuilder setAddedTrips(List<Trip> addedTrips) {
            driver.addedTrips = addedTrips;
            return this;
        }

        public Driver build() {
            return driver;
        }
    }
}
