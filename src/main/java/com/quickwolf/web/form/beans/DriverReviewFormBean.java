package com.quickwolf.web.form.beans;

import com.quickwolf.domain.Rating;

public class DriverReviewFormBean {
    private int ratingOrdinal;

    public int getRatingOrdinal() {
        return ratingOrdinal;
    }

    public void setRatingOrdinal(final int ratingOrdinal) {
        this.ratingOrdinal = ratingOrdinal;
    }

    public Rating getRating() {
        Rating[] ratings = Rating.values();
        return (ratingOrdinal < ratings.length) ? ratings[ratingOrdinal] : Rating.NONE;
    }
}
