package com.quickwolf.web.service;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;

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
}
