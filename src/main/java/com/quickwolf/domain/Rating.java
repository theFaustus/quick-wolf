package com.quickwolf.domain;

import java.util.Comparator;

public enum Rating {
    NONE(0), ONE_STAR(1), TWO_STARS(2), THREE_STARS(3), FOUR_STARS(4), FIVE_STARS(5);

    private final int ratingAmount;

    Rating(final int ratingAmount) {
        this.ratingAmount = ratingAmount;
    }

    public int getRatingAmount() {
        return ratingAmount;
    }

    public static final Comparator<Rating> RATING_AMOUNT_COMPARATOR = Comparator.comparing(Rating::getRatingAmount);
}
