package com.quickwolf.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.form.beans.UpdatePasswordFormBean;
import com.quickwolf.web.form.beans.UpdateProfileFormBean;
import com.quickwolf.web.repository.PassengerRepository;
import com.quickwolf.web.service.PassengerService;

/**
 * Created by Faust on 4/19/2017.
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Transactional
    @Override
    public Passenger savePassenger(RegisterPassengerFormBean registerPassengerFormBean) {
        Passenger passenger = Passenger.newBuilder()
                .setFirstName(registerPassengerFormBean.getFirstName())
                .setLastName(registerPassengerFormBean.getLastName())
                .setEmail(registerPassengerFormBean.getEmail())
                .setPassword(registerPassengerFormBean.getPassengerPassword())
                .setTelephoneNumber(registerPassengerFormBean.getTelephoneNumber())
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

    @Transactional
    @Override
    public void updateEnabledValue(String email, int enabledPassenger) {
        passengerRepository.updateEnabledValue(email, enabledPassenger);
    }

    @Override
    public Passenger findPassengerByEmailWithFetchedTrips(String email) {
        return passengerRepository.findPassengerByEmailWithFetchedTrips(email);
    }

    @Override
    public List<Passenger> findAllPassengersWithFetchedBookedTrips() {
        return passengerRepository.findAllPassengersWithFetchedBookedTrips();
    }

    @Transactional
    @Override
    public void updatePassword(final String userEmail, final UpdatePasswordFormBean formBean) {
        passengerRepository.updatePassword(userEmail, formBean.getNewPassword());
    }

    @Transactional
    @Override
    public void updateProfile(final String email, final UpdateProfileFormBean formBean) {
        Passenger passenger = passengerRepository.findPassengerBy(email);
        passenger.setFirstName(formBean.getFirstName());
        passenger.setLastName(formBean.getLastName());
        passenger.setTelephoneNumber(formBean.getPhoneNumber());
        passengerRepository.save(passenger);
    }
}
