package com.quickwolf.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DriverTest {

    Driver driver = new Driver();

    @Test
    public void newlyCreatedDriverShouldHaveNoneRating() {
        assertEquals(Rating.NONE, driver.getOverallRating());
    }

    @Test
    public void driverWithOneStarReviewShouldHaveOneStarOverallRating() {
        setDriverReviews(Rating.ONE_STAR);
        assertEquals(Rating.ONE_STAR, driver.getOverallRating());
    }

    @Test
    public void driverWithTwoStarReviewShouldHaveTwoStarOverallRating() {
        setDriverReviews(Rating.TWO_STARS);
        assertEquals(Rating.TWO_STARS, driver.getOverallRating());
    }

    @Test
    public void driverWithTwoOneStarReviewsShouldHaveOneStarOverallRating() {
        setDriverReviews(Rating.ONE_STAR, Rating.ONE_STAR);
        assertEquals(Rating.ONE_STAR, driver.getOverallRating());
    }

    @Test
    public void driverWithOneStarAndFiveStarReviewsShouldHaveThreeStarOverallRating() {
        setDriverReviews(Rating.ONE_STAR, Rating.FIVE_STARS);
        assertEquals(Rating.THREE_STARS, driver.getOverallRating());
    }

    @Test
    public void driverWithOneStarAndTwoStarReviewsShouldHaveOneStarOverallRating() {
        setDriverReviews(Rating.ONE_STAR, Rating.TWO_STARS);
        assertEquals(Rating.ONE_STAR, driver.getOverallRating());
    }

    private void setDriverReviews(Rating... ratings) {
        for (Rating r : ratings) {
            driver.getReviews().add(new Review(r));
        }
    }
}
