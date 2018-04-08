package com.quickwolf.web.controller;

import com.quickwolf.domain.MailSender;
import com.quickwolf.domain.Passenger;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.BookTripFormBean;

/**
 * Created by Faust on 4/23/2017.
 */
@Controller
@SessionAttributes({"bookedTrip", "tripId"})
public class BookTripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(value = "/bookingConfirmed", method = RequestMethod.GET)
    public String bookConfirmed(@ModelAttribute("tripId") long tripId) {
        if (tripId == 0)
            return "redirect:/error";
        System.out.println(tripId);
        return "bookingConfirmed";
    }

    @RequestMapping(value = "/bookTrip", method = RequestMethod.POST)
    public String bookConfirmed(Model model, @ModelAttribute("bookedTrip") BookTripFormBean bookTripFormBean, RedirectAttributes flashAttributes) {
        try {
            Trip t = tripService.findById(bookTripFormBean.getTripId());
            Passenger p = passengerService.findPassengerBy(bookTripFormBean.getemail());
            tripService.bookTrip(bookTripFormBean.getemail(), bookTripFormBean.getTripId());
            MailSender mailSender = new MailSender(bookTripFormBean.getemail());
            mailSender.sendInvoice(p, t);
        } catch (Exception e) {
            return "redirect:/error";
        }
        System.out.println("good");
        flashAttributes.addFlashAttribute("tripId", bookTripFormBean.getTripId());
        return "redirect:/bookingConfirmed";
    }

    @RequestMapping(value = "/cancelTrip", method = RequestMethod.POST)
    public String cancelTrip(@RequestParam long tripId, @RequestParam String email) {
        tripService.cancelTrip(email, tripId);
        System.out.println("good");
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
