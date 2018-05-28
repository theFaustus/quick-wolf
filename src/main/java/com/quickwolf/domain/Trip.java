package com.quickwolf.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quickwolf.exception.NotEnoughFreeSeatsException;

@Entity
@Table(name = "_trip", schema = "wolf")
public class Trip extends AbstractEntity {

    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "from_country")),
            @AttributeOverride(name = "countryCode", column = @Column(name = "from_country_code")),
            @AttributeOverride(name = "state", column = @Column(name = "from_state")),
            @AttributeOverride(name = "city", column = @Column(name = "from_city")),
            @AttributeOverride(name = "street", column = @Column(name = "from_street")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "from_zip_code")),
    })
    private Address fromAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "destination_country")),
            @AttributeOverride(name = "countryCode", column = @Column(name = "destination_country_code")),
            @AttributeOverride(name = "state", column = @Column(name = "destination_state")),
            @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
            @AttributeOverride(name = "street", column = @Column(name = "destination_street")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "destination_zip_code"))
    })
    private Address destinationAddress;

    @Column(name = "trip_time")
    @Temporal(TemporalType.TIME)
    @JsonIgnore
    private Date tripTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "arrive_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arriveTime;

    @Column(name = "arrive_date")
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date arriveDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "depart_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departTime;

    @Column(name = "depart_date")
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date departDate;

    @Column(name = "trip_price")
    private BigDecimal price = BigDecimal.ZERO;

    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL)
    private Itinerary itinerary;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Driver driver;

    @Column(name = "available_seats")
    private int availableSeats;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private List<Passenger> passengers = new ArrayList<>();

    public Trip() {
    }

    public void addPassenger(Passenger passenger) {
        if (availableSeats > 0) {
            --availableSeats;
            passengers.add(passenger);
            passenger.addTrip(this);
        } else {
            throw new NotEnoughFreeSeatsException();
        }
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public String getTripDuration() {
        return getDurationAsString(Duration.between(departTime.toInstant(), arriveTime.toInstant()));
    }

    private String getDurationAsString(Duration duration) {
        StringBuilder durationString = new StringBuilder();
        if (duration.toDays() > 0) {
            String postfix = duration.toDays() == 1 ? "" : "s";
            durationString.append(duration.toDays() + " day");
            durationString.append(postfix);
        }

        duration = duration.minusDays(duration.toDays());
        long hours = duration.toHours();
        if (hours > 0) {
            String prefix = StringUtils.isEmpty(durationString.toString()) ? "" : ", ";
            String postfix = hours == 1 ? "" : "s";
            durationString.append(prefix);
            durationString.append(hours + " hour");
            durationString.append(postfix);
        }

        duration = duration.minusHours(duration.toHours());
        long minutes = duration.toMinutes();
        if (minutes > 0) {
            String prefix = StringUtils.isEmpty(durationString.toString()) ? "" : ", ";
            String postfix = minutes == 1 ? "" : "s";
            durationString.append(prefix);
            durationString.append(minutes + " minute");
            durationString.append(postfix);
        }

        return durationString.toString();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + getId() +
                ", fromAddress=" + fromAddress +
                ", destinationAddress=" + destinationAddress +
                ", tripTime=" + tripTime +
                ", arriveTime=" + arriveTime +
                ", price=" + price +
                '}';
    }

    public static TripBuilder newBuilder() {
        return new TripBuilder();
    }

    public static final class TripBuilder {

        private Long id;

        private Address fromAddress;

        private Address destinationAddress;

        private Date tripTime;

        private Date arriveTime;

        private Date arriveDate;

        private Date departTime;

        private Date departDate;

        private BigDecimal price = BigDecimal.ZERO;

        private Itinerary itinerary;

        private Driver driver;

        private int availableSeats;

        private TripBuilder() {
        }

        public TripBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TripBuilder setFromAddress(Address fromAddress) {
            this.fromAddress = fromAddress;
            return this;
        }

        public TripBuilder setDestinationAddress(Address destinationAddress) {
            this.destinationAddress = destinationAddress;
            return this;
        }

        public TripBuilder setTripTime(Date tripTime) {
            this.tripTime = tripTime;
            return this;
        }

        public TripBuilder setArriveTime(Date arriveTime) {
            this.arriveTime = arriveTime;
            return this;
        }

        public TripBuilder setArriveDate(Date arriveDate) {
            this.arriveDate = arriveDate;
            return this;
        }

        public TripBuilder setDepartTime(Date departTime) {
            this.departTime = departTime;
            return this;
        }

        public TripBuilder setDepartDate(Date departDate) {
            this.departDate = departDate;
            return this;
        }

        public TripBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public TripBuilder setItinerary(Itinerary itinerary) {
            this.itinerary = itinerary;
            return this;
        }

        public TripBuilder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public TripBuilder setAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(id);
            trip.setFromAddress(fromAddress);
            trip.setDestinationAddress(destinationAddress);
            trip.setTripTime(tripTime);
            trip.setArriveTime(arriveTime);
            trip.setArriveDate(arriveDate);
            trip.setDepartTime(departTime);
            trip.setDepartDate(departDate);
            trip.setPrice(price);
            trip.setItinerary(itinerary);
            trip.setDriver(driver);
            trip.setAvailableSeats(availableSeats);
            return trip;
        }
    }
}
