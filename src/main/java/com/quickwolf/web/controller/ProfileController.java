package com.quickwolf.web.controller;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.MailSender;
import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.repository.DriverRepository;
import com.quickwolf.web.repository.PassengerRepository;
import com.quickwolf.web.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Faust on 5/17/2017.
 */
@Controller
public class ProfileController {

    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    DriverRepository driverRepository;

    @RequestMapping(value = "/passengerProfile")
    public String profilePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Passenger p = passengerRepository.findPassengerBy(auth.getName());
        List<Trip> bookedTrips = tripRepository.findBookedTrips(auth.getName());
        System.out.println(bookedTrips);
        p.setBookedTrips(bookedTrips);
        model.addAttribute("passenger", p);
        return "passengerProfile";
    }

    @RequestMapping(value = "/driverProfile")
    public String driverPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Driver d = tripRepository.findDriverBy(auth.getName());
        List<Trip> addedTrips = tripRepository.findAddedTrips(auth.getName());
        System.out.println(addedTrips);
        d.setAddedTrips(addedTrips);
        model.addAttribute("driver", d);
        return "driverProfile";
    }

    @RequestMapping(value = "/adminProfile")
    public String adminPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Passenger> listOfPassengers = passengerRepository.findAllPassengers();
        for(Passenger p : listOfPassengers){
            p.setBookedTrips(tripRepository.findBookedTrips(p.getPassengerEmail()));
        }
        List<Driver> listOfDrivers = tripRepository.findAllDrivers();
        for(Driver d : listOfDrivers){
            d.setAddedTrips(tripRepository.findAddedTrips(d.getDriverEmail()));
        }
        model.addAttribute("passengers", listOfPassengers);
        model.addAttribute("drivers", listOfDrivers);
        return "adminProfile";
    }

    @RequestMapping(value = "/disableDriver", method = RequestMethod.POST)
    public String disableDriver(@RequestParam int enabledDriver, @RequestParam String driverEmail) {
        driverRepository.updateEnabledValue(driverEmail, enabledDriver);
        MailSender mailSender = new MailSender(driverEmail);
        mailSender.sendBlockingWarning(driverEmail);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/disablePassenger", method = RequestMethod.POST)
    public String disablePassenger(@RequestParam int enabledPassenger, @RequestParam String passengerEmail) {
        passengerRepository.updateEnabledValue(passengerEmail, enabledPassenger);
        MailSender mailSender = new MailSender(passengerEmail);
        mailSender.sendBlockingWarning(passengerEmail);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/enableDriver", method = RequestMethod.POST)
    public String enableDriver(@RequestParam int enabledDriver, @RequestParam String driverEmail) {
        driverRepository.updateEnabledValue(driverEmail, enabledDriver);
        MailSender mailSender = new MailSender(driverEmail);
        mailSender.sendUnblockingWarning(driverEmail);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/enablePassenger", method = RequestMethod.POST)
    public String enablePassenger(@RequestParam int enabledPassenger, @RequestParam String passengerEmail) {
        passengerRepository.updateEnabledValue(passengerEmail, enabledPassenger);
        MailSender mailSender = new MailSender(passengerEmail);
        mailSender.sendUnblockingWarning(passengerEmail);
        return "redirect:/adminProfile";
    }
}
