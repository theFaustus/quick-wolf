package com.quickwolf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

/**
 * Created by Faust on 4/20/2017.
 */
@Entity
@Table(name = "_transport", schema = "wolf")
public class Transport extends AbstractEntity {

    @Column(name = "transport_name")
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Column(name = "model_name")
    @NotNull
    @Size(min = 2, max = 30)
    private String modelName;

    @Column(name = "transport_type")
    @NotNull
    @Pattern(regexp = "(car|bus)")
    private String transportType;

    @Column(name = "body_type")
    @NotNull
    @Pattern(regexp = "(suv|truck|sedan|van|coupe|wagon|convertible|sports|diesel|crossover|luxury|hybrid|hatchback|microbus|autobus|bus)")
    private String bodyType;

    @Column(name = "number_of_seats")
    @NotNull
    @Min(value = 2)
    @Max(value = 100)
    private int numberOfSeats;

    @Column(name = "registration_number")
    @NotNull
    @Pattern(regexp = "\\w{1,3}\\d{1,3}")
    private String registrationNumber;

    @Column(name = "date_of_registration")
    @NotNull
    @Pattern(regexp = "^([0-9]{4})-([1-9]|1[0-2])-([0-9]|1[0-9]|2[0-9]|3[0-1])$")
    private String dateOfRegistration;

    @Column(name = "transport_year")
    @NotNull
    @Pattern(regexp = "\\d{4}")
    private String year;

    @Column(name = "transport_color")
    @NotNull
    @Size(min = 3, max = 30)
    private String color;

    @Column(name = "engine_id")
    @NotNull
    @Size(min = 14, max = 14)
    private String engineId;

    @OneToOne
    private Driver driver;

    public Transport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "name='" + name + '\'' +
                ", modelName='" + modelName + '\'' +
                ", transportType='" + transportType + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                ", year='" + year + '\'' +
                ", color='" + color + '\'' +
                ", engineId='" + engineId + '\'' +
                '}';
    }

    public static TransportBuilder newBuilder() {
        return new TransportBuilder();
    }

    public static final class TransportBuilder {
        private Long id;
        private String name;
        private String modelName;
        private String transportType;
        private String bodyType;
        private int numberOfSeats;
        private String registrationNumber;
        private String dateOfRegistration;
        private String year;
        private String color;

        private String engineId;

        private TransportBuilder() {
        }

        public TransportBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TransportBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TransportBuilder setModelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public TransportBuilder setTransportType(String transportType) {
            this.transportType = transportType;
            return this;
        }

        public TransportBuilder setBodyType(String bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public TransportBuilder setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public TransportBuilder setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public TransportBuilder setDateOfRegistration(String dateOfRegistration) {
            this.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public TransportBuilder setYear(String year) {
            this.year = year;
            return this;
        }

        public TransportBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public TransportBuilder setEngineId(String engineId) {
            this.engineId = engineId;
            return this;
        }

        public Transport build() {
            Transport transport = new Transport();
            transport.setId(id);
            transport.setName(name);
            transport.setModelName(modelName);
            transport.setTransportType(transportType);
            transport.setBodyType(bodyType);
            transport.setNumberOfSeats(numberOfSeats);
            transport.setRegistrationNumber(registrationNumber);
            transport.setDateOfRegistration(dateOfRegistration);
            transport.setYear(year);
            transport.setColor(color);
            transport.setEngineId(engineId);
            return transport;
        }
    }
}
