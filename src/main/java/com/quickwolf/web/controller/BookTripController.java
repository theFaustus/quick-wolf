package com.quickwolf.web.controller;

import com.quickwolf.domain.*;
import com.quickwolf.web.service.EmailService;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TicketService;
import com.quickwolf.web.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quickwolf.web.form.beans.BookTripFormBean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

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

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/bookingConfirmed", method = RequestMethod.GET)
    public String bookConfirmed(@ModelAttribute("tripId") long tripId) {
        if (tripId == 0)
            return "redirect:/error";
        LOGGER.info(tripId);
        return "bookingConfirmed";
    }

    @RequestMapping(value = "/bookTrip", method = RequestMethod.POST)
    public String bookConfirmed(Model model, @ModelAttribute("bookedTrip") BookTripFormBean bookTripFormBean, RedirectAttributes flashAttributes) {
        try {
            Trip t = tripService.findById(bookTripFormBean.getTripId());
            Passenger p = passengerService.findPassengerBy(bookTripFormBean.getemail());
            tripService.bookTrip(bookTripFormBean.getemail(), bookTripFormBean.getTripId());
            Ticket ticket = Ticket.newBuilder()
                    .setTicketType(TicketType.SIMPLE)
                    .setTrip(t)
                    .setUser(p)
                    .build();
            Order o = Order.newBuilder().setTicket(ticket).build();
            tripService.saveOrder(o);

            File ticketFile = ticketService.createTicket(o, "mail/ticket");
            Email email = buildEmail(ticketFile, renderEmailBody(o), bookTripFormBean.getemail());
            emailService.sendEmail(email);
            ticketFile.delete();

            //MailSender mailSender = new MailSender(bookTripFormBean.getemail());
            //mailSender.sendInvoice(p, t);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        LOGGER.info("good");
        flashAttributes.addFlashAttribute("tripId", bookTripFormBean.getTripId());
        return "redirect:/bookingConfirmed";
    }

    private Email buildEmail(File ticket, String emailBody, String email) {
        return Email.newBuilder()
                .loadProperties(buildProperties())
                .setAttachedFiles(Arrays.asList(ticket.getAbsolutePath()))
                .setMessage(emailBody)
                .setSubject("Ticket reservation")
                .setToEmail(email)
                .build();
    }

    private String renderEmailBody(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        String emailBody = templateEngine.process("mail/email", context);
        return emailBody;
    }

    private Properties buildProperties() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/emailConfig.properties"));
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
