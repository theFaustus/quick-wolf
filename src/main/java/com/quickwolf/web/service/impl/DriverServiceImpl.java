package com.quickwolf.web.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Email;
import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Review;
import com.quickwolf.domain.Trip;
import com.quickwolf.util.Constants;
import com.quickwolf.web.WebUtil;
import com.quickwolf.web.form.beans.DriverReviewFormBean;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;
import com.quickwolf.web.form.beans.UpdateProfileFormBean;
import com.quickwolf.web.repository.DriverRepository;
import com.quickwolf.web.service.DriverService;
import com.quickwolf.web.service.EmailService;
import com.quickwolf.web.service.PassengerService;
import com.quickwolf.web.service.TripService;

/**
 * Created by Faust on 4/20/2017.
 */
@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger LOGGER = Logger.getLogger(DriverServiceImpl.class);
    private static final int DRIVER_DISABLED_STATUS = 0;
    private static final int DRIVER_ENABLED_STATUS = 1;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private WebUtil webUtil;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TripService tripService;

    @Transactional
    @Override
    public Driver saveDriver(RegisterDriverFormBean bean) {
        Driver driver = Driver.newBuilder()
                .setFirstName(bean.getFirstName())
                .setLastName(bean.getLastName())
                .setEmail(bean.getEmail())
                .setPassword(bean.getDriverPassword())
                .setTelephoneNumber(bean.getTelephoneNumber())
                .setIdnp(bean.getIdnp())
                .setDateOfBirth(bean.getDateOfBirth())
                .setTransport(bean.getTransport())
                .build();
        driverRepository.save(driver);
        return driver;
    }

    @Override
    public Driver findDriverBy(String email) {
        return driverRepository.findDriverBy(email);
    }

    @Override
    public List<Trip> findAddedTrips(String email) {
        return driverRepository.findAddedTrips(email);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Transactional
    @Override
    public void updateEnabledValue(String email, int enabledDriver) {
        driverRepository.updateEnabledValue(email, enabledDriver);
    }

    @Transactional
    @Override
    public Driver disableDriver(String driverEmail) {
        updateEnabledValue(driverEmail, DRIVER_DISABLED_STATUS);
        Driver driver = findDriverBy(driverEmail);
        sendAccountDisableNotification(driver);
        return driver;
    }

    @Transactional
    @Override
    public void enableDriver(String driverEmail) {
        updateEnabledValue(driverEmail, DRIVER_ENABLED_STATUS);
        Driver driver = findDriverBy(driverEmail);
        sendAccountEnableNotification(driver);
    }

    @Override
    public Driver findDriverByEmailWithFetchedAddedTrips(String email) {
        return driverRepository.findDriverByEmailWithFetchedAddedTrips(email);
    }

    @Override
    public List<Driver> findAllDriversWithFetchedAddedTrips() {
        return driverRepository.findAllDriversWithFetchedAddedTrips();
    }

    @Transactional
    @Override
    public void updateProfile(final String email, final UpdateProfileFormBean formBean) {
        Driver driver = driverRepository.findDriverBy(email);
        driver.setFirstName(formBean.getFirstName());
        driver.setLastName(formBean.getLastName());
        driver.setTelephoneNumber(formBean.getPhoneNumber());
        driverRepository.save(driver);
    }

    @Transactional
    @Override
    public void addDriverReview(final long driverId, final DriverReviewFormBean formBean) {
        LOGGER.info("Trying to add review for: " + driverId);
        driverRepository.findDriverByIdWithFetchedReviews(driverId).ifPresent(driver -> {
            if (!passengerReviewedTrip(formBean.getTripId(), driver, getPassengerEmail())) {
                addNewReview(formBean, driver);
            } else {
                getPassengerReview(formBean.getTripId(), driver, getPassengerEmail()).ifPresent(review -> updateReview(formBean, driver, review));
            }
        });
    }

    private void updateReview(final DriverReviewFormBean formBean, final Driver driver, final Review review) {
        review.setRating(formBean.getRating());
        driverRepository.save(driver);
        LOGGER.info("Successfully updated driver rating: " + formBean.getRating());
    }

    private void addNewReview(final DriverReviewFormBean formBean, final Driver driver) {
        Passenger passenger = passengerService.findPassengerBy(getPassengerEmail());
        Trip trip = tripService.findById(formBean.getTripId());
        driver.addReview(new Review(formBean.getRating(), passenger, trip));
        driverRepository.save(driver);
        LOGGER.info("Successfully added driver rating: " + formBean.getRating());
    }

    private String getPassengerEmail() {
        return webUtil.getLoggedUserEmail();
    }

    private Optional<Review> getPassengerReview(final long tripId, final Driver driver, final String passengerEmail) {
        return driver.getReviews()
                .stream()
                .filter(r -> r.getReviewer().getEmail().equals(passengerEmail))
                .filter(r -> r.getTrip().getId().equals(tripId))
                .findAny();
    }

    private boolean passengerReviewedTrip(final long tripId, final Driver driver, final String passengerEmail) {
        return getPassengerReview(tripId, driver, passengerEmail).isPresent();
    }

    private void sendAccountEnableNotification(Driver driver) {
        String emailBody = renderEmailBody(driver, Constants.Driver.ENABLE_ACCOUNT_TEMPLATE_NAME);
        Email email = buildEmail(emailBody, driver.getEmail(), Constants.Driver.ENABLE_ACCOUNT_SUBJECT);
        emailService.sendEmail(email);
    }

    private void sendAccountDisableNotification(Driver driver) {
        String emailBody = renderEmailBody(driver, Constants.Driver.DISABLE_ACCOUNT_TEMPLATE_NAME);
        Email email = buildEmail(emailBody, driver.getEmail(), Constants.Driver.DISABLE_ACCOUNT_SUBJECT);
        emailService.sendEmail(email);
    }

    private String renderEmailBody(Driver driver, String templateName) {
        Context context = new Context();
        context.setVariable("userName", String.format("%s %s", driver.getFirstName(), driver.getLastName()));
        return templateEngine.process(templateName, context);
    }

    private Email buildEmail(String emailBody, String driverEmail, String subject) {
        return Email.newBuilder()
                .loadProperties(buildProperties())
                .setMessage(emailBody)
                .setSubject(subject)
                .setToEmail(driverEmail)
                .build();
    }

    private Properties buildProperties() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/emailConfig.properties"));
            return props;
        } catch (IOException e) {
            LOGGER.error("Error while loading email properties.", e);
            throw new RuntimeException(e);
        }
    }
}
