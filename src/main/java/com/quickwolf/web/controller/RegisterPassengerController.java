package com.quickwolf.web.controller;

import com.quickwolf.domain.Passenger;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;
import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TripService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Faust on 4/19/2017.
 */
@Controller
@RequestMapping(value = "/registerAsPassenger")
public class RegisterPassengerController {

    @Autowired
    private PassengerService service;

    @GetMapping
    public String register(Model model) {
        return "registerAsPassenger";
    }

    @PostMapping
    public String registerPassenger(@ModelAttribute("registerPassengerFormBean") @Valid RegisterPassengerFormBean registerPassengerFormBean,
                                    BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }


        if(bindingResult.hasErrors()) {
            System.out.println("lame");
            return "registerAsPassenger";
        }
        else {
            service.savePassenger(registerPassengerFormBean);
            System.out.println("well done");
            return "redirect:/login";
        }

    }

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("registerPassengerFormBean", new RegisterPassengerFormBean());
    }
}
