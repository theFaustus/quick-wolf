package com.quickwolf.web.controller;

import com.quickwolf.domain.MailSender;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.PassengerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Faust on 5/17/2017.
 */
@Controller
public class ProfileController {
    private static final Logger LOGGER = Logger.getLogger(ProfileController.class);

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/passengerProfile")
    public String showPassengerProfilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("passenger", passengerService.findPassengerByEmailWithFetchedTrips(auth.getName()));
        return "passengerProfile";
    }

    @RequestMapping(value = "/driverProfile")
    public String showDriverProfilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("driver", driverService.findDriverByEmailWithFetchedAddedTrips(auth.getName()));
        return "driverProfile";
    }

    @GetMapping("/adminProfile")
    public String showAdminPage(Model model) {
        model.addAttribute("passengers", passengerService.findAllPassengersWithFetchedBookedTrips());
        model.addAttribute("drivers", driverService.findAllDriversWithFetchedAddedTrips());
        return "adminProfile";
    }

    @PostMapping("/disableDriver")
    public String disableDriver(@RequestParam int enabledDriver, @RequestParam("email") String driverEmail) {
        driverService.disableDriver(driverEmail);
        return "redirect:/adminProfile";
    }

    @PostMapping("/disablePassenger")
    public String disablePassenger(@RequestParam int enabledPassenger, @RequestParam String email) {
        passengerService.updateEnabledValue(email, enabledPassenger);
        MailSender mailSender = new MailSender(email);
        mailSender.sendBlockingWarning(email);
        return "redirect:/adminProfile";
    }

    @PostMapping("/enableDriver")
    public String enableDriver(@RequestParam int enabledDriver, @RequestParam("email") String driverEmail) {
        driverService.enableDriver(driverEmail);
        return "redirect:/adminProfile";
    }

    @PostMapping("/enablePassenger")
    public String enablePassenger(@RequestParam int enabledPassenger, @RequestParam("email") String passengerEmail) {
        passengerService.updateEnabledValue(passengerEmail, enabledPassenger);
        MailSender mailSender = new MailSender(passengerEmail);
        mailSender.sendUnblockingWarning(passengerEmail);
        return "redirect:/adminProfile";
    }
}
