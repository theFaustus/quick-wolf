package com.quickwolf.web.form.beans;

import com.quickwolf.domain.Rating;

public class DriverReviewFormBean {
    private int ratingOrdinal;
    private long tripId;

    public int getRatingOrdinal() {
        return ratingOrdinal;
    }

    public void setRatingOrdinal(final int ratingOrdinal) {
        this.ratingOrdinal = ratingOrdinal;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(final long tripId) {
        this.tripId = tripId;
    }

    public Rating getRating() {
        Rating[] ratings = Rating.values();
        return (ratingOrdinal < ratings.length) ? ratings[ratingOrdinal] : Rating.NONE;
    }
}
