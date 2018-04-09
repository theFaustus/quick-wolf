package com.quickwolf.web.controller;

import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/registerAsDriver")
    public String showDriverRegistrationForm(Model model) {
        model.addAttribute("registerDriverFormBean", new RegisterDriverFormBean());
        return "registerAsDriver";
    }

    @PostMapping("/registerAsDriver")
    public String registerDriver(@ModelAttribute("registerDriverFormBean") @Valid RegisterDriverFormBean registerDriverFormBean,
                                 BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/";
        if(bindingResult.hasErrors())
            return "registerAsDriver";
        else {
            driverService.saveDriver(registerDriverFormBean);
            return "redirect:/login";
        }
    }

    @GetMapping("/registerAsPassenger")
    public String showPassengerRegistrationForm(Model model) {
        model.addAttribute("registerPassengerFormBean", new RegisterPassengerFormBean());
        return "registerAsPassenger";
    }

    @PostMapping("/registerAsPassenger")
    public String registerPassenger(@ModelAttribute("registerPassengerFormBean") @Valid RegisterPassengerFormBean registerPassengerFormBean,
                                    BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/";
        if(bindingResult.hasErrors())
            return "registerAsPassenger";
        else {
            passengerService.savePassenger(registerPassengerFormBean);
            return "redirect:/login";
        }
    }
}
