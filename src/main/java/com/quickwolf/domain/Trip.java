package com.quickwolf.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class Trip {
    private Long id;
    private Address fromAddress;
    private Address destinationAddress;
    private Date tripTime;
    private Date arriveTime;
    private BigDecimal price = BigDecimal.ZERO;
    private Itinerary itinerary;
    private Driver driver;
    private int availableSeats;

    public Trip() {
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(Address fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Date getTripTime() {
        return tripTime;
    }

    public void setTripTime(Date tripTime) {
        this.tripTime = tripTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Duration getTripDuration() {
        return Duration.between(arriveTime.toInstant(), tripTime.toInstant());
    }

    public static TripBuilder newTrip() {
        return new TripBuilder();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", fromAddress=" + fromAddress +
                ", destinationAddress=" + destinationAddress +
                ", tripTime=" + tripTime +
                ", arriveTime=" + arriveTime +
                ", price=" + price +
                ", itinerary=" + itinerary +
                ", driver=" + driver +
                ", availableSeats=" + availableSeats +
                '}';
    }

    public static class TripBuilder {
        private Trip trip = new Trip();

        public TripBuilder setFromAddress(Address fromAddress) {
            trip.fromAddress = fromAddress;
            return this;
        }

        public TripBuilder setDestinationAddress(Address destinationAddress) {
            trip.destinationAddress = destinationAddress;
            return this;
        }

        public TripBuilder setTripTime(Date tripTime) {
            trip.tripTime = tripTime;
            return this;
        }

        public TripBuilder setArriveTime(Date arriveTime) {
            trip.arriveTime = arriveTime;
            return this;
        }

        public TripBuilder setPrice(BigDecimal price) {
            trip.price = price;
            return this;
        }

        public TripBuilder setItinerary(Itinerary itinerary) {
            trip.itinerary = itinerary;
            return this;
        }

        public TripBuilder setId(long id) {
            trip.id = id;
            return this;
        }

        public TripBuilder setAvailableSeats(int availableSeats) {
            trip.availableSeats = availableSeats;
            return this;
        }

        public TripBuilder setDriver(Driver d) {
            trip.driver = d;
            return this;
        }

        public Trip build() {
            return trip;
        }
    }
}
