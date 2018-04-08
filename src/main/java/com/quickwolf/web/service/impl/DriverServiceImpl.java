package com.quickwolf.web.service.impl;

import com.quickwolf.domain.Driver;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.repository.DriverRepository;
import com.quickwolf.web.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Faust on 4/20/2017.
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Override
    public Driver saveDriver(RegisterDriverFormBean registerDriverFormBean) {
        Driver driver = Driver.newDriver()
                .driverId(new Random().nextInt(1000))
                .firstName(registerDriverFormBean.getFirstName())
                .lastName(registerDriverFormBean.getLastName())
                .email(registerDriverFormBean.getDriverEmail())
                .password(registerDriverFormBean.getDriverPassword())
                .telephoneNumber(registerDriverFormBean.getTelephoneNumber())
                .humanId(registerDriverFormBean.getHumanId())
                .dateOfBirth(registerDriverFormBean.getDateOfBirth())
                .creditCards(registerDriverFormBean.getCreditCard())
                .registeredTransport(registerDriverFormBean.getRegisteredTransport())
                .build();
        driverRepository.saveDriver(driver);
        return driver;
    }
}
