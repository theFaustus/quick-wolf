package com.quickwolf.web.service;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.DriverReviewFormBean;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.form.beans.UpdateProfileFormBean;

import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
public interface DriverService {
    Driver saveDriver(RegisterDriverFormBean registerDriverFormBean);

    Driver findDriverBy(String email);

    List<Trip> findAddedTrips(String email);

    List<Driver> findAll();

    void updateEnabledValue(String email, int enabledDriver);

    Driver disableDriver(String driverEmail);

    void enableDriver(String driverEmail);

    Driver findDriverByEmailWithFetchedAddedTrips(String email);

    List<Driver> findAllDriversWithFetchedAddedTrips();

    void updateProfile(String email, UpdateProfileFormBean formBean);

    void addDriverReview(final long driverId, DriverReviewFormBean formBean);
}
