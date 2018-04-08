package com.quickwolf.web.service.impl;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.repository.DriverRepository;
import com.quickwolf.web.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Driver saveDriver(RegisterDriverFormBean registerDriverFormBean) {
        Driver driver = Driver.newBuilder()
                .setFirstName(registerDriverFormBean.getFirstName())
                .setLastName(registerDriverFormBean.getLastName())
                .setEmail(registerDriverFormBean.getemail())
                .setPassword(registerDriverFormBean.getDriverPassword())
                .setTelephoneNumber(registerDriverFormBean.getTelephoneNumber())
                .setIdnp(registerDriverFormBean.getHumanId())
                .setDateOfBirth(registerDriverFormBean.getDateOfBirth())
                .setCreditCard(registerDriverFormBean.getCreditCard())
                .setTransport(registerDriverFormBean.gettransport())
                .build();
        driverRepository.save(driver);
        return driver;
    }

    @Override
    public Driver findDriverBy(String email) {
        return driverRepository.findDriverBy(email);
    }

    @Override
    public List<Trip> findAddedTrips(String email) {
        return driverRepository.findAddedTrips(email);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public void updateEnabledValue(String email, int enabledDriver) {
        driverRepository.updateEnabledValue(email, enabledDriver);
    }
}
