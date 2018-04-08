package com.quickwolf.web.service.impl;

import com.quickwolf.domain.Passenger;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.repository.PassengerRepository;
import com.quickwolf.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Faust on 4/19/2017.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    @Override
    public Passenger savePassenger(RegisterPassengerFormBean registerPassengerFormBean) {
        Passenger passenger = Passenger.newPassenger()
                .passengerId(new Random().nextInt(1000))
                .firstName(registerPassengerFormBean.getFirstName())
                .lastName(registerPassengerFormBean.getLastName())
                .email(registerPassengerFormBean.getPassengerEmail())
                .password(registerPassengerFormBean.getPassengerPassword())
                .telephoneNumber(registerPassengerFormBean.getTelephoneNumber())
                .creditCard(registerPassengerFormBean.getCreditCard())
                .bookedTrips(new ArrayList<>())
                .build();
        passengerRepository.savePassenger(passenger);
        return passenger;
    }
}
