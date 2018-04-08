package com.quickwolf.web.controller;

import com.quickwolf.domain.MailSender;
import com.quickwolf.domain.Passenger;
import com.quickwolf.web.repository.PassengerRepository;
import com.quickwolf.web.repository.TripRepository;
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
    private TripRepository tripRepository;
    @Autowired
    private PassengerRepository passengerRepository;

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
            Trip t = tripRepository.findTripBy(bookTripFormBean.getTripId());
            Passenger p = passengerRepository.findPassengerBy(bookTripFormBean.getPassengerEmail());
            passengerRepository.bookTrip(bookTripFormBean.getPassengerEmail(), bookTripFormBean.getTripId());
            MailSender mailSender = new MailSender(bookTripFormBean.getPassengerEmail());
            mailSender.sendInvoice(p, t);
        } catch (Exception e) {
            return "redirect:/error";
        }
        System.out.println("good");
        flashAttributes.addFlashAttribute("tripId", bookTripFormBean.getTripId());
        return "redirect:/bookingConfirmed";
    }

    @RequestMapping(value = "/cancelTrip", method = RequestMethod.POST)
    public String cancelTrip(@RequestParam long tripId, @RequestParam String passengerEmail) {
        passengerRepository.cancelTrip(passengerEmail, tripId);
        System.out.println("good");
        return "redirect:/passengerProfile";
    }

    @RequestMapping(value = "/confirmTripBooking", method = RequestMethod.GET)
    public String book(Model model,
                       @ModelAttribute("bookedTrip") BookTripFormBean bookTripFormBean) {
        Trip t = tripRepository.findTripBy(bookTripFormBean.getTripId());
        Passenger p = passengerRepository.findPassengerBy(bookTripFormBean.getPassengerEmail());
        model.addAttribute("trip", t);
        model.addAttribute("passenger", p);
        return "confirmTripBooking";
    }

    @RequestMapping(value = "/confirmTripBooking", method = RequestMethod.POST)
    public String bookTrip(@RequestParam long tripId, @RequestParam String passengerEmail,
                           RedirectAttributes flashAttributes) {
        flashAttributes.addFlashAttribute("bookedTrip", new BookTripFormBean(passengerEmail, tripId));
        return "redirect:/confirmTripBooking";

    }
}
