package com.quickwolf.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "review")
public class Review extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @JsonIgnore
    @ManyToOne
    private Driver driver;

    @JsonIgnore
    @ManyToOne
    private Passenger reviewer;

    @JsonIgnore
    @ManyToOne
    private Trip trip;

    public Review() {
    }

    public Review(final Rating rating) {
        this.rating = rating;
    }

    public Review(final Rating rating, final Passenger reviewer, final Trip trip) {
        this.rating = rating;
        this.reviewer = reviewer;
        this.trip = trip;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(final Rating rating) {
        this.rating = rating;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(final Driver driver) {
        this.driver = driver;
    }

    public Passenger getReviewer() {
        return reviewer;
    }

    public void setReviewer(final Passenger reviewer) {
        this.reviewer = reviewer;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(final Trip trip) {
        this.trip = trip;
    }
}
