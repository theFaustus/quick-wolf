package com.quickwolf.web.controller;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.BookTripFormBean;
import com.quickwolf.web.service.EmailService;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TicketService;
import com.quickwolf.web.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;

/**
 * Created by Faust on 4/23/2017.
 */
@Controller
@SessionAttributes({"bookedTrip", "tripId"})
public class BookTripController {
    private static final Logger LOGGER = Logger.getLogger(BookTripController.class);

    @Autowired
    private TripService tripService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(value = "/bookingConfirmed", method = RequestMethod.GET)
    public String bookConfirmed(@ModelAttribute("tripId") long tripId) {
        if (tripId == 0)
            return "redirect:/error";
        LOGGER.info(tripId);
        return "bookingConfirmed";
    }

    @RequestMapping(value = "/bookTrip", method = RequestMethod.POST)
    public String bookConfirmed(@ModelAttribute("bookedTrip") BookTripFormBean bookTripFormBean, RedirectAttributes flashAttributes) {
        tripService.bookTrip(bookTripFormBean.getemail(), bookTripFormBean.getTripId());
        flashAttributes.addFlashAttribute("tripId", bookTripFormBean.getTripId());
        return "redirect:/bookingConfirmed";
    }

    @RequestMapping(value = "/cancelTrip", method = RequestMethod.POST)
    public String cancelTrip(@RequestParam long tripId, @RequestParam String email) {
        tripService.cancelTrip(email, tripId);
        LOGGER.info("good");
        return "redirect:/passengerProfile";
    }

    @RequestMapping(value = "/confirmTripBooking", method = RequestMethod.GET)
    public String book(Model model,
                       @ModelAttribute("bookedTrip") BookTripFormBean bookTripFormBean) {
        Trip t = tripService.findById(bookTripFormBean.getTripId());
        Passenger p = passengerService.findPassengerBy(bookTripFormBean.getemail());
        model.addAttribute("trip", t);
        model.addAttribute("passenger", p);
        return "confirmTripBooking";
    }

    @RequestMapping(value = "/confirmTripBooking", method = RequestMethod.POST)
    public String bookTrip(@RequestParam long tripId, @RequestParam String email,
                           RedirectAttributes flashAttributes) {
        flashAttributes.addFlashAttribute("bookedTrip", new BookTripFormBean(email, tripId));
        return "redirect:/confirmTripBooking";

    }
}
