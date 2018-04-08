package com.quickwolf.web.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.quickwolf.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.quickwolf.web.form.beans.TripFormBean;
import com.quickwolf.web.repository.TripRepository;

@Repository
public class JdbcTripRepository implements TripRepository {
    private static final String SELECT_TRIPS_QUERY_BY_SEARCH_CRITERIAS = "select t.id, depart_time, arrive_time, "
            + "from_country, from_city, from_street, destination_country, destination_city, "
            + "destination_street, price, available_seats from trip t where from_country = ? and from_city = ? and "
            + "destination_country = ? and destination_city = ? and Date(depart_time) = ?";

    private static final String SELECT_TRIP_BY_ID_QUERY = "select t.id, depart_time, arrive_time, "
            + "from_country, from_city, from_street, destination_country, destination_city, "
            + "destination_street, price, available_seats from trip t where t.id = ?";

    private static final String SELECT_TRIP_ITINERARY = "select it.id, its.name, its.arrive, "
            + "its.depart, country, city, street from itinerary it join itinerary_steps its "
            + "on it.id = its.itinerary_id where trip_id = ?";

    private static final String SELECT_BOOKED_TRIPS_BY_PASSENGER = "select t.id, depart_time, arrive_time, "
            + "from_country, from_city, from_street, destination_country, destination_city, "
            + "destination_street, price, available_seats from trip t join passenger_trips pt on t.id = pt.trip_id where pt.passenger_email = ?";

    private static final String SELECT_ADDED_TRIPS_BY_PASSENGER = "select t.id, depart_time, arrive_time, "
            + "from_country, from_city, from_street, destination_country, destination_city, "
            + "destination_street, price, available_seats from trip t join driver_trips dt on t.id = dt.trip_id where dt.driver_email = ?";

    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_DRIVERS = "select d.id, first_name, last_name, " +
            "date_of_birth, human_id, email, password, telephone_number, enabled from driver d join user_driver " +
            "ud on d.id = ud.driver_id join users u on ud.user_id = u.id where role = 'ROLE_DRIVER'";

    private static final String SELECT_DRIVER_BY_EMAIL = "select d.id, first_name, last_name, " +
            "date_of_birth, human_id, email, password, telephone_number, enabled from driver d join user_driver " +
            "ud on d.id = ud.driver_id join users u on ud.user_id = u.id where email = ?";

    private static final String SELECT_DRIVER_BY_TRIP_ID_QUERY = "select d.id, first_name, last_name, " +
            "date_of_birth, human_id, email, password, telephone_number, enabled from driver d join user_driver " +
            "ud on d.id = ud.driver_id join users u on ud.user_id = u.id join driver_trips dt on " +
            "u.email = dt.driver_email join trip t on dt.trip_id = t.id where t.id = ?";

    private static final String SELECT_TRANSPORT_BY_DRIVER_ID_QUERY = "select tr.id, name, model_name, transport_type, " +
            "body_type, number_of_seats, registration_number, date_of_registration, year, color, engine_id from " +
            "transport tr join driver d on tr.driver_id = d.id where d.id = ?";

    private static final String SELECT_CREDIT_CARD_BY_DRIVER_ID_QUERY = "select c.id, c.first_name, " +
            "c.last_name, c.card_number, c.expiration_month, c.expiration_year, c.security_code, " +
            "c.user_id from driver_credit_card c join driver p on ? = c.user_id";

    private class DriverRowMapper implements RowMapper<Driver> {
        @Override
        public Driver mapRow(ResultSet rs, int i) throws SQLException {
            Driver d = Driver.newDriver()
                    .driverId(rs.getLong(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .dateOfBirth(rs.getString(4))
                    .humanId(rs.getString(5))
                    .email(rs.getString(6))
                    .password(rs.getString(7))
                    .telephoneNumber(rs.getString(8))
                    .enabled(rs.getInt(9))
                    .addedTrips(new ArrayList<>())
                    .build();
            d.setRegisteredTransport(getTransportByDriverId(d.getDriverId()));
            d.setCreditCard(getDriverCreditCard(d.getDriverId()));
            return d;
        }
    }

    private Driver getDriverByTripId(Long id) {
        List<Driver> drivers = jdbcTemplate.query(SELECT_DRIVER_BY_TRIP_ID_QUERY, new Long[] { id },
                new JdbcTripRepository.DriverRowMapper());
        return !drivers.isEmpty() ? drivers.get(0) : Driver.newDriver().build();
    }

    private class CreditCardRowMapper implements RowMapper<CreditCard> {
        public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
            CreditCard c = CreditCard.newCreditCard()
                    .creditCardId(rs.getLong(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .cardNumber(rs.getString(4))
                    .expirationMonth(rs.getString(5))
                    .expirationYear(rs.getString(6))
                    .securityCode(rs.getString(7))
                    .build();
            return c;
        }
    }

    private CreditCard getDriverCreditCard(Long id) {
        List<CreditCard> creditCards = jdbcTemplate.query(SELECT_CREDIT_CARD_BY_DRIVER_ID_QUERY, new Long[] { id },
                new JdbcTripRepository.CreditCardRowMapper());
        return !creditCards.isEmpty() ? creditCards.get(0) : CreditCard.newCreditCard().build();
    }

    private class TransportRowMapper implements RowMapper<Transport> {
        public Transport mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transport t = Transport.newTransport()
                    .transportId(rs.getLong(1))
                    .name(rs.getString(2))
                    .modelName(rs.getString(3))
                    .transportType(rs.getString(4))
                    .bodyType(rs.getString(5))
                    .numberOfSeats(rs.getString(6))
                    .registrationNumber(rs.getString(7))
                    .dateOfRegistration(rs.getString(8))
                    .year(rs.getString(9))
                    .color(rs.getString(10))
                    .engineId(rs.getString(11))
                    .build();
            return t;
        }
    }

    private Transport getTransportByDriverId(Long id) {
        List<Transport> transports = jdbcTemplate.query(SELECT_TRANSPORT_BY_DRIVER_ID_QUERY, new Long[] { id },
                new JdbcTripRepository.TransportRowMapper());
        return !transports.isEmpty() ? transports.get(0) : Transport.newTransport().build();
    }

    @Override
    public List<Trip> findTripsBy(TripFormBean trip) {
        Object[] queryParameters = {
                trip.getFromCountry(), trip.getFromCity(),
                trip.getToCountry(), trip.getToCity(),
                dateFormat.get().format(trip.getDepart())
        };
        return jdbcTemplate.query(SELECT_TRIPS_QUERY_BY_SEARCH_CRITERIAS, queryParameters, new TripRowMapper());
    }

    @Override
    public List<Trip> findBookedTrips(String email) {
        return jdbcTemplate.query(SELECT_BOOKED_TRIPS_BY_PASSENGER, new String[] {email}, new TripRowMapper());
    }

    @Override
    public List<Trip> findAddedTrips(String email) {
        return jdbcTemplate.query(SELECT_ADDED_TRIPS_BY_PASSENGER, new String[] {email}, new TripRowMapper());
    }

    @Override
    public Driver findDriverBy(String email) {
        return jdbcTemplate.query(SELECT_DRIVER_BY_EMAIL, new String[] {email}, new DriverRowMapper()).get(0);
    }


    @Override
    public Trip findTripBy(long id) {
        return jdbcTemplate.query(SELECT_TRIP_BY_ID_QUERY, new Long[]{id}, new TripRowMapper()).get(0);
    }

    private class TripRowMapper implements RowMapper<Trip> {
        public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trip t = Trip.newTrip()
                    .setId(rs.getLong(1))
                    .setTripTime(rs.getTimestamp(2))
                    .setArriveTime(rs.getTimestamp(3))
                    .setFromAddress(extractAddress(rs, 4))
                    .setDestinationAddress(extractAddress(rs, 7))
                    .build();
            t.setPrice(rs.getBigDecimal(10));
            t.setAvailableSeats(rs.getInt(11));
            t.setItinerary(getTripItinerary(t.getId()));
            t.setDriver(getDriverByTripId(t.getId()));
            return t;
        }
    }

    private Address extractAddress(ResultSet rs, int startingIndex) throws SQLException {
        return Address.newBuilder()
                .setCountry(rs.getString(0 + startingIndex))
                .setCity(rs.getString(1 + startingIndex))
                .setStreet(rs.getString(2 + startingIndex))
                .build();
    }

    @Override
    public List<Driver> findAllDrivers() {
        return jdbcTemplate.query(SELECT_ALL_DRIVERS, new DriverRowMapper());
    }

    private Itinerary getTripItinerary(Long id) {
        List<Itinerary> itinearies = jdbcTemplate.query(SELECT_TRIP_ITINERARY, new Long[]{id},
                new ItineraryRowMapper());
        return !itinearies.isEmpty() ? itinearies.get(0) : new Itinerary();
    }

    private class ItineraryRowMapper implements RowMapper<Itinerary> {
        Itinerary itinerary = new Itinerary();

        public Itinerary mapRow(ResultSet rs, int rowNum) throws SQLException {
            itinerary.setId(rs.getLong(1));
            ItineraryStep step = ItineraryStep.newBuilder()
                    .setName(rs.getString(2))
                    .setArrive(rs.getTimestamp(3))
                    .setDepart(rs.getTimestamp(4))
                    .setAddress(extractAddress(rs, 5))
                    .build();
            itinerary.addItineraryStep(step);
            return itinerary;
        }
    }

    @Override
    public Trip createTrip(Trip t) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("trip")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("depart_time", t.getTripTime())
                        .addValue("arrive_time", t.getArriveTime())
                        .addValue("from_country", t.getFromAddress().getCountry())
                        .addValue("from_city", t.getFromAddress().getCity())
                        .addValue("from_street", t.getFromAddress().getStreet())
                        .addValue("destination_country", t.getDestinationAddress().getCountry())
                        .addValue("destination_city", t.getDestinationAddress().getCity())
                        .addValue("destination_street", t.getDestinationAddress().getStreet())
                        .addValue("price", t.getPrice())
                        .addValue("available_seats", t.getAvailableSeats()));
        t.setId(key.longValue());
        insertTripItinerary(t);
        insertLinkedDriver(t);
        return t;
    }

    private void insertTripItinerary(Trip t) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("itinerary")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("trip_id", t.getId()));
        t.getItinerary().setId(key.longValue());
        insertItinerarySteps(t);
    }

    private void insertLinkedDriver(Trip t) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("driver_trips")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("driver_email", t.getDriver().getDriverEmail())
                        .addValue("trip_id", t.getId()));
    }

    private void insertItinerarySteps(Trip t) {
        for (ItineraryStep step : t.getItinerary().getSteps()) {
            Number key = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("itinerary_steps")
                    .usingGeneratedKeyColumns("id")
                    .executeAndReturnKey(new MapSqlParameterSource()
                            .addValue("name", step.getName())
                            .addValue("arrive", step.getArrive())
                            .addValue("depart", step.getDepart())
                            .addValue("country", step.getAddress().getCountry())
                            .addValue("city", step.getAddress().getCity())
                            .addValue("street", step.getAddress().getStreet())
                            .addValue("itinerary_id", t.getItinerary().getId()));
            step.setId(key.longValue());
        }
    }
}
