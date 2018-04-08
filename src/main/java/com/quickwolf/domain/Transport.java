package com.quickwolf.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Faust on 4/20/2017.
 */
public class Transport {
    private long transportId;
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "Name of your car should be between 2 - 30 characters long")
    private String name;
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "Model of your car should be between 2 - 30 characters long")
    private String modelName;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "(car|bus)", message = "You`re allowed to use a bus or a car")
    private String transportType;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "(suv|truck|sedan|van|coupe|wagon|convertible|sports|diesel|crossover|luxury|hybrid|hatchback|microbus|autobus|bus)", message = "Allowed types for car : suv, truck, sedan, van, coupe, wagon, convertible, sports, diesel, crossover, luxury, hybrid, hatchback and for bus : microbus, autobus, bus")
    private String bodyType;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "\\d{1,3}", message = "Use a valid number of seats")
    private String numberOfSeats;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "\\w{1,3}\\d{1,3}", message = "Use a valid registration number")
    private String registrationNumber;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "^([0-9]{4})-([1-9]|1[0-2])-([0-9]|1[0-9]|2[0-9]|3[0-1])$", message = "Enter the date of birth in form yyyy-mm-dd.")
    private String dateOfRegistration;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "\\d{4}", message = "Use a valid year of car")
    private String year;
    @NotNull(message = "You have to fill this element")
    @Size(min = 3, max = 30, message = "Color should be 3 - 30 characters long")
    private String color;
    @NotNull(message = "You have to fill this element")
    @Size(min = 14, max = 14, message = "Engine id should be 14 characters long according to your registration certificate")
    private String engineId;

    private Transport(Builder builder) {
        this.transportId = builder.transportId;
        this.name = builder.name;
        this.modelName = builder.modelName;
        this.transportType = builder.transportType;
        this.bodyType = builder.bodyType;
        this.numberOfSeats = builder.numberOfSeats;
        this.registrationNumber = builder.registrationNumber;
        this.dateOfRegistration = builder.dateOfRegistration;
        this.year = builder.year;
        this.color = builder.color;
        this.engineId = builder.engineId;
    }

    public static Builder newTransport() {
        return new Builder();
    }


    public static final class Builder {
        private long transportId;
        private String name;
        private String modelName;
        private String transportType;
        private String bodyType;
        private String numberOfSeats;
        private String registrationNumber;
        private String dateOfRegistration;
        private String year;
        private String color;
        private String engineId;

        private Builder() {
        }

        public Transport build() {
            return new Transport(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder modelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public Builder transportType(String carType) {
            this.transportType = carType;
            return this;
        }

        public Builder bodyType(String bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public Builder numberOfSeats(String numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Builder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder dateOfRegistration(String dateOfRegistration) {
            this.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public Builder year(String year) {
            this.year = year;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder engineId(String engineId) {
            this.engineId = engineId;
            return this;
        }

        public Builder transportId(long transportId) {
            this.transportId = transportId;
            return this;
        }
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

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
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

    public long getTransportId() {
        return transportId;
    }

    public void setTransportId(long transportId) {
        this.transportId = transportId;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "name='" + name + '\'' +
                ", modelName='" + modelName + '\'' +
                ", transportType='" + transportType + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", numberOfSeats='" + numberOfSeats + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                ", year='" + year + '\'' +
                ", color='" + color + '\'' +
                ", engineId='" + engineId + '\'' +
                '}';
    }
}
