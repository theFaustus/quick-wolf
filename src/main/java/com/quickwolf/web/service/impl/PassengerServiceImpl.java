package com.quickwolf.web.service.impl;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.repository.PassengerRepository;
import com.quickwolf.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Faust on 4/19/2017.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger savePassenger(RegisterPassengerFormBean registerPassengerFormBean) {
        Passenger passenger = Passenger.newBuilder()
                .setFirstName(registerPassengerFormBean.getFirstName())
                .setLastName(registerPassengerFormBean.getLastName())
                .setEmail(registerPassengerFormBean.getEmail())
                .setPassword(registerPassengerFormBean.getPassengerPassword())
                .setTelephoneNumber(registerPassengerFormBean.getTelephoneNumber())
                .setCreditCard(registerPassengerFormBean.getCreditCard())
                .build();
        passengerRepository.save(passenger);
        return passenger;
    }

    @Override
    public Passenger findPassengerBy(String email) {
        return passengerRepository.findPassengerBy(email);
    }

    @Override
    public List<Trip> findBookedTrips(String passengerEmail) {
        return passengerRepository.findBookedTrips(passengerEmail);
    }

    @Override
    public List<Passenger> findAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public void updateEnabledValue(String email, int enabledPassenger) {
        passengerRepository.updateEnabledValue(email, enabledPassenger);
    }
}
