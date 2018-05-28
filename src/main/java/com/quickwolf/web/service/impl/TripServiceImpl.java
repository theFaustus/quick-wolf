package com.quickwolf.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.quickwolf.domain.*;
import com.quickwolf.web.repository.CountryRepository;
import com.quickwolf.web.repository.OrderRepository;
import com.quickwolf.web.service.*;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickwolf.web.form.beans.AddTripFormBean;
import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.repository.TripRepository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TripServiceImpl implements TripService {
    private static final Logger LOGGER = Logger.getLogger(TripServiceImpl.class);

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private DriverService driverService;

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private EmailService emailService;

    @Autowired
    private TicketService ticketService;

    @Autowired
	private CountryRepository countryRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Trip> findTripsBy(TripFormBean t) {
	    t = t.trimAndLowercaseFields();
		List<Trip> trips = tripRepository.findTripsBy(t.getFromCountry(),
                t.getFromCity(), t.getToCountry(),
				t.getToCity(), t.getDepartDate());
		for (Trip trip : trips) {
			Hibernate.initialize(trip.getItinerary().getSteps());
			Hibernate.initialize(trip.getDriver().getReviews());
		}
		LOGGER.info("Searching by criteria: " + t);
		LOGGER.info("Found trips: " + trips);
		return sortTripsByDriverRatingDesc(trips);
	}

	private List<Trip> sortTripsByDriverRatingDesc(final List<Trip> trips) {
		return trips.stream()
				.sorted(this::compareTripsByDriverRatingDesc)
				.collect(Collectors.toList());
	}

	private int compareTripsByDriverRatingDesc(Trip firstTrip, Trip secondTrip) {
		return Rating.RATING_AMOUNT_COMPARATOR.reversed()
				.compare(firstTrip.getDriver().getOverallRating(), secondTrip.getDriver().getOverallRating());
	}

	@Transactional
	@Override
	public Trip createTrip(AddTripFormBean trip, String driverEmail) {
		Country fromCountry = countryRepository.findByValue(trip.getFromCountry());
		Country destinationCountry = countryRepository.findByValue(trip.getToCountry());
		Trip t = trip.toTrip();
		t.getFromAddress().setCountryCode(fromCountry.getId());
		t.getDestinationAddress().setCountryCode(destinationCountry.getId());
		List<ItineraryStep> steps = t.getItinerary().getSteps();
		for(ItineraryStep s : steps){
			s.getAddress().setCountryCode(countryRepository.findByValue(s.getAddress().getCountry()).getId());
		}
		t.setDriver(driverService.findDriverBy(driverEmail));
		return tripRepository.save(t);
	}

	@Override
	public Trip findById(long tripId) {
		Optional<Trip> trip = tripRepository.findById(tripId);
		return trip.orElse(null);
	}

	@Transactional
	@Override
	public boolean bookTrip(String passengerEmail, long tripId) {
	    LOGGER.info("Trying to book trip: " + tripId + " by passenger: " + passengerEmail);
        Passenger passenger = passengerService.findPassengerBy(passengerEmail);
        return tripRepository.findById(tripId).map(t -> makeTicketAndSendItViaEmail(passenger, t)).orElse(false);
	}

    private boolean makeTicketAndSendItViaEmail(Passenger passenger, Trip trip) {
	    try {
            return tryMakeTicketAndSendItViaEmail(passenger, trip);
        } catch (Exception e) {
            LOGGER.error("Error while booking trip.", e);
        }
        return false;
    }

    private boolean tryMakeTicketAndSendItViaEmail(Passenger passenger, Trip trip) throws Exception {
        trip.addPassenger(passenger);
        tripRepository.save(trip);
        Ticket ticket = Ticket.newBuilder()
                .setTicketType(TicketType.SIMPLE)
                .setTrip(trip)
                .setUser(passenger)
                .build();
        Order order = Order.newBuilder().setTicket(ticket).build();
        saveOrder(order);
        File ticketFile = ticketService.createTicket(order, "mail/ticket");
        emailService.sendEmail(buildEmail(ticketFile, renderEmailBody(order), passenger.getEmail()));
        Files.delete(ticketFile.toPath());
        LOGGER.info("Trip successfully booked.");
        return true;
    }

    private Email buildEmail(File ticket, String emailBody, String email) {
		return Email.newBuilder()
				.loadProperties(buildProperties())
				.setAttachedFiles(Collections.singletonList(ticket.getAbsolutePath()))
				.setMessage(emailBody)
				.setSubject("Ticket reservation")
				.setToEmail(email)
				.build();
	}

	private String renderEmailBody(Order order) {
		Context context = new Context();
		context.setVariable("order", order);
		return templateEngine.process("mail/email", context);
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

	@Override
	public void cancelTrip(String email, long tripId) {

	}

	@Override
	public Order saveOrder(Order o) {
		return orderRepository.save(o);
	}

    @Override
    public List<Trip> findValidTripsBy(final TripFormBean trip) {
	    final Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return findTripsBy(trip).stream()
                .filter(t -> t.getAvailableSeats() > 0)
                .filter(t -> t.getDepartTime().after(today))
                .collect(Collectors.toList());
    }
}
