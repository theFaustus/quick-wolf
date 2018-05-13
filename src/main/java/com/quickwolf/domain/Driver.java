package com.quickwolf.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quickwolf.util.Constants;

/**
 * Created by Faust on 4/20/2017.
 */
@Entity
@Table(name = "_driver", schema = "wolf")
public class Driver extends User {

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "driver_idnp")
    private String idnp;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Transport transport;

    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Trip> addedTrips = new ArrayList<>();

    public Driver() {
        setRole(Constants.DEFAULT_DRIVER_ROLE);
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
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", idnp='" + idnp + '\'' +
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
            driver.setFirstName(firstName);
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
            driver.setLastName(lastName);
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
            driver.setTelephoneNumber(telephoneNumber);
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
