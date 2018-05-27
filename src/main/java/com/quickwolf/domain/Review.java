package com.quickwolf.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @ManyToOne
    private Driver driver;

    public Review() {
    }

    public Review(final Rating rating) {
        this.rating = rating;
    }

    public Review(final Rating rating, final Driver driver) {
        this.rating = rating;
        this.driver = driver;
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
}
