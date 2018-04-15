package com.quickwolf.web.controller;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.MailSender;
import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.repository.DriverRepository;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TripService;
import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(ProfileController.class);

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TripService tripService;

    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/passengerProfile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Passenger p = passengerService.findPassengerBy(auth.getName());
        List<Trip> bookedTrips = passengerService.findBookedTrips(auth.getName());
        LOGGER.info(bookedTrips);
        p.setBookedTrips(bookedTrips);
        model.addAttribute("passenger", p);
        return "passengerProfile";
    }

    @RequestMapping(value = "/driverProfile")
    public String driverPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Driver d = driverService.findDriverBy(auth.getName());
        List<Trip> addedTrips = driverService.findAddedTrips(auth.getName());
        LOGGER.info(addedTrips);
        d.setAddedTrips(addedTrips);
        model.addAttribute("driver", d);
        return "driverProfile";
    }

    @RequestMapping(value = "/adminProfile")
    public String adminPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Passenger> listOfPassengers = passengerService.findAllPassengers();
        for (Passenger p : listOfPassengers) {
            p.setBookedTrips(passengerService.findBookedTrips(p.getEmail()));
        }
        List<Driver> listOfDrivers = driverService.findAll();
        for (Driver d : listOfDrivers) {
            d.setAddedTrips(driverService.findAddedTrips(d.getEmail()));
        }
        model.addAttribute("passengers", listOfPassengers);
        model.addAttribute("drivers", listOfDrivers);
        return "adminProfile";
    }

    @RequestMapping(value = "/disableDriver", method = RequestMethod.POST)
    public String disableDriver(@RequestParam int enabledDriver, @RequestParam String email) {
        driverService.updateEnabledValue(email, enabledDriver);
        MailSender mailSender = new MailSender(email);
        mailSender.sendBlockingWarning(email);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/disablePassenger", method = RequestMethod.POST)
    public String disablePassenger(@RequestParam int enabledPassenger, @RequestParam String email) {
        passengerService.updateEnabledValue(email, enabledPassenger);
        MailSender mailSender = new MailSender(email);
        mailSender.sendBlockingWarning(email);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/enableDriver", method = RequestMethod.POST)
    public String enableDriver(@RequestParam int enabledDriver, @RequestParam String email) {
        driverService.updateEnabledValue(email, enabledDriver);
        MailSender mailSender = new MailSender(email);
        mailSender.sendUnblockingWarning(email);
        return "redirect:/adminProfile";
    }

    @RequestMapping(value = "/enablePassenger", method = RequestMethod.POST)
    public String enablePassenger(@RequestParam int enabledPassenger, @RequestParam String email) {
        passengerService.updateEnabledValue(email, enabledPassenger);
        MailSender mailSender = new MailSender(email);
        mailSender.sendUnblockingWarning(email);
        return "redirect:/adminProfile";
    }
}
