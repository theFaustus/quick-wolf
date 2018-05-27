package com.quickwolf.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.WebUtil;
import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.service.TripService;

@Controller
public class TripsController {

    @Autowired
    private TripService service;

    @Autowired
    private WebUtil webUtil;

    @RequestMapping(value = "/listTrips")
    public String listTrips(Model model, @ModelAttribute("trips") List<Trip> trips) {
        model.addAttribute("trips", trips);
        return "listTrips";
    }

    @RequestMapping(value = "/addTrip", method = RequestMethod.GET)
    public String addTrip(Model model) {
        model.addAttribute("tripBean", new AddTripFormBean());
        return "addTrip";
    }

    @RequestMapping(value = "/addTrip", method = RequestMethod.POST)
    public String createNewTrip(@ModelAttribute("tripBean") AddTripFormBean trip, Model model) {
        service.createTrip(trip, webUtil.getLoggedUserEmail());
        return "redirect:/";
    }
}
