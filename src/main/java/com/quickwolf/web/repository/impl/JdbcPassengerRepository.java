package com.quickwolf.web.repository.impl;

import com.quickwolf.domain.*;
import com.quickwolf.web.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faust on 4/22/2017.
 */
@Repository
public class JdbcPassengerRepository implements PassengerRepository {

    private static final String SELECT_PASSENGER_BY_ID_QUERY = "select p.id, first_name, last_name, email, password, telephone_number, enabled from passenger p join user_passenger up on\n" +
            "p.id = passenger_id join users u on up.user_id = u.id where p.id like ?";

    private static final String SELECT_ALL_PASSENGERS = "select p.id, first_name, last_name, email, password, telephone_number, enabled from passenger p join user_passenger up on\n" +
            "p.id = passenger_id join users u on up.user_id = u.id where role like 'ROLE_USER'";

    private static final String SELECT_PASSENGER_BY_EMAIL_QUERY = "select p.id, first_name, last_name, email, password, telephone_number, enabled from passenger p join user_passenger up on\n" +
            "p.id = passenger_id join users u on up.user_id = u.id where u.email like ?";

    private static final String SELECT_CREDIT_CARD_BY_PASSENGER_ID_QUERY = "select c.id, c.first_name, " +
            "c.last_name, c.card_number, c.expiration_month, c.expiration_year, c.security_code, " +
            "c.user_id from passenger_credit_card c join passenger p on ? = c.user_id";

    private static final String SELECT_AVAILABLE_SEATS_BY_TRIP_ID = "select available_seats from trip t where t.id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class PassengerRowMapper implements RowMapper<Passenger> {
        public Passenger mapRow(ResultSet rs, int rowNum) throws SQLException {
            Passenger p = Passenger.newPassenger()
                    .passengerId(rs.getLong(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .email(rs.getString(4))
                    .password(rs.getString(5))
                    .telephoneNumber(rs.getString(6))
                    .enabled(rs.getInt(7))
                    .bookedTrips(new ArrayList<>())
                    .build();
            p.setCreditCard(getCreditCard(p.getPassengerId()));
            return p;
        }
    }

    private class AvailableSeatsRowMapper implements RowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt(1);
        }
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

    private CreditCard getCreditCard(Long id) {
        List<CreditCard> creditCards = jdbcTemplate.query(SELECT_CREDIT_CARD_BY_PASSENGER_ID_QUERY, new Long[] { id },
                new JdbcPassengerRepository.CreditCardRowMapper());
        return !creditCards.isEmpty() ? creditCards.get(0) : CreditCard.newCreditCard().build();
    }

    private int getAvailableSeats(Long id) {
        List<Integer> availableSeatsList = jdbcTemplate.query(SELECT_AVAILABLE_SEATS_BY_TRIP_ID, new Long[] { id },
                new JdbcPassengerRepository.AvailableSeatsRowMapper());
        return !availableSeatsList.isEmpty() ? availableSeatsList.get(0) : 0;
    }

    private long insertPassengerInUsers(Passenger p) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("email", p.getPassengerEmail())
                        .addValue("password", p.getPassengerPassword())
                        .addValue("enabled", 1)
                        .addValue("role", "ROLE_USER"));
        return key.longValue();
    }

    private void insertPassengerInLinkTable(long userId, long passengerId) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_passenger")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("user_id", userId)
                        .addValue("passenger_id", passengerId));
    }

    @Override
    public List<Passenger> findAllPassengers() {
        return jdbcTemplate.query(SELECT_ALL_PASSENGERS, new PassengerRowMapper());
    }

    @Override
    public void updateEnabledValue(String email, int value) {
        jdbcTemplate.update("update users set enabled = ? where email = ?", value, email);
    }

    @Override
    public Passenger savePassenger(Passenger p) {
        Long userId = insertPassengerInUsers(p);
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("passenger")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("first_name", p.getFirstName())
                        .addValue("last_name", p.getLastName())
                        .addValue("telephone_number", p.getTelephoneNumber()));
        p.setPassengerId(key.longValue());
        insertPassengerInLinkTable(userId, p.getPassengerId());
        insertPassengerCreditCard(p);
        return p;
    }

    @Override
    public void bookTrip(String passengerEmail, long tripId) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("passenger_trips")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("passenger_email", passengerEmail)
                        .addValue("trip_id", tripId)
               );
        int availableSeats = getAvailableSeats(tripId);
        if (availableSeats == 0){
            throw new RuntimeException("Seats not available");
        }
        availableSeats--;
        jdbcTemplate.update("update trip set available_seats = ? where trip.id = ?", availableSeats, tripId);
    }

    @Override
    public void cancelTrip(String passengerEmail, long tripId) {
        int availableSeats = getAvailableSeats(tripId);
        availableSeats++;
        jdbcTemplate.update("update trip set available_seats = ? where trip.id = ?", availableSeats, tripId);
        jdbcTemplate.update("delete from passenger_trips where trip_id = ? and passenger_email = ? limit 1", tripId, passengerEmail);
    }

    @Override
    public Passenger findPassengerBy(long id) {
        return jdbcTemplate.query(SELECT_PASSENGER_BY_ID_QUERY, new Long[] {id}, new PassengerRowMapper()).get(0);
    }

    @Override
    public Passenger findPassengerBy(String email) {
        return jdbcTemplate.query(SELECT_PASSENGER_BY_EMAIL_QUERY, new String[] {email}, new PassengerRowMapper()).get(0);
    }

    private void insertPassengerCreditCard(Passenger p) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("passenger_credit_card")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("user_id", p.getPassengerId())
                        .addValue("first_name", p.getCreditCard().getFirstName())
                        .addValue("last_name", p.getCreditCard().getLastName())
                        .addValue("card_number", p.getCreditCard().getCardNumber())
                        .addValue("expiration_month", p.getCreditCard().getExpirationMonth())
                        .addValue("expiration_year", p.getCreditCard().getExpirationYear())
                        .addValue("security_code", p.getCreditCard().getSecurityCode())
                );
        p.getCreditCard().setCreditCardId(key.longValue());
    }
}
