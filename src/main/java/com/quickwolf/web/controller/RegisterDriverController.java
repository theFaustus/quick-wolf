package com.quickwolf.web.controller;

import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.service.DriverService;
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

import javax.validation.Valid;

/**
 * Created by Faust on 4/20/2017.
 */
@Controller
@RequestMapping(value = "/registerAsDriver")
public class RegisterDriverController {

    @Autowired
    private DriverService service;

    @GetMapping
    public String register(Model model) {
        return "registerAsDriver";
    }

    @PostMapping
    public String registerPassenger(@ModelAttribute("registerDriverFormBean") @Valid RegisterDriverFormBean registerDriverFormBean,
                                    BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if(bindingResult.hasErrors()) {
            System.out.println("lame");
            return "registerAsDriver";
        }
        else {
            service.saveDriver(registerDriverFormBean);
            System.out.println("well done");
            return "redirect:/login";
        }

    }

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("registerDriverFormBean", new RegisterDriverFormBean());
    }
}
