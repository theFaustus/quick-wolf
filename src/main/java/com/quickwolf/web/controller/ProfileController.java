package com.quickwolf.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.MailSender;
import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.User;
import com.quickwolf.web.WebUtil;
import com.quickwolf.web.form.beans.UpdatePasswordFormBean;
import com.quickwolf.web.form.beans.UpdateProfileFormBean;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.validator.PasswordUpdateValidator;

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

    @Autowired
    private PasswordUpdateValidator passwordUpdateValidator;

    @Autowired
    private WebUtil webUtil;

    @GetMapping("/passengerProfile")
    public String showPassengerProfilePage(Model model) {
        populatePassengerProfileModel(model);
        return "passengerProfile";
    }

    private void populatePassengerProfileModel(final Model model) {
        Passenger passenger = passengerService.findPassengerByEmailWithFetchedTrips(webUtil.getLoggedUserEmail());
        model.addAttribute("passenger", passenger);
        model.addAttribute("updatePasswordFormBean", new UpdatePasswordFormBean());
        model.addAttribute("updateProfileFormBean", makeUpdateProfileFormBean(passenger));
    }

    private UpdateProfileFormBean makeUpdateProfileFormBean(final User user) {
        return UpdateProfileFormBean.newBuilder()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setPhoneNumber(user.getTelephoneNumber())
                .build();
    }

    @PostMapping("/updatePassword")
    public String updatePassword(Model model, UpdatePasswordFormBean formBean, BindingResult result) {
        populatePassengerProfileModel(model);
        return updateUserPassword(formBean, result, "passengerProfile");
    }

    private String updateUserPassword(UpdatePasswordFormBean formBean, BindingResult result, String targetViewName) {
        passwordUpdateValidator.validate(formBean, result);
        if (result.hasErrors()) {
            return targetViewName;
        }
        passengerService.updatePassword(webUtil.getLoggedUserEmail(), formBean);
        return "redirect:/" + targetViewName;
    }

    @PostMapping("/updateProfile")
    public String updateProfile(UpdateProfileFormBean formBean) {
        passengerService.updateProfile(webUtil.getLoggedUserEmail(), formBean);
        return "redirect:/passengerProfile";
    }

    @PostMapping("/updateDriverProfile")
    public String updateDriverProfile(UpdateProfileFormBean formBean) {
        driverService.updateProfile(webUtil.getLoggedUserEmail(), formBean);
        return "redirect:/driverProfile";
    }

    @RequestMapping(value = "/driverProfile")
    public String showDriverProfilePage(Model model) {
        Driver driver = driverService.findDriverByEmailWithFetchedAddedTrips(webUtil.getLoggedUserEmail());
        model.addAttribute("driver", driver);
        model.addAttribute("updateProfileFormBean", makeUpdateProfileFormBean(driver));
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
