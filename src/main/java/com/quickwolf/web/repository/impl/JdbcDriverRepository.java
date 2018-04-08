package com.quickwolf.web.repository.impl;

import com.quickwolf.domain.Driver;
import com.quickwolf.web.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * Created by Faust on 4/22/2017.
 */
@Repository
public class JdbcDriverRepository implements DriverRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Driver saveDriver(Driver d) {
        Long userId = insertDriverInUsers(d);
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("driver")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("first_name", d.getFirstName())
                        .addValue("last_name", d.getLastName())
                        .addValue("date_of_birth", d.getDateOfBirth())
                        .addValue("human_id", d.getHumanId())
                        .addValue("telephone_number", d.getTelephoneNumber()));
        d.setDriverId(key.longValue());
        insertDriverInLinkTable(userId, d.getDriverId());
        insertDriverCreditCard(d);
        insertDriverTransport(d);
        return d;
    }

    private void insertDriverCreditCard(Driver d) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("driver_credit_card")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("user_id", d.getDriverId())
                        .addValue("first_name", d.getCreditCard().getFirstName())
                        .addValue("last_name", d.getCreditCard().getLastName())
                        .addValue("card_number", d.getCreditCard().getCardNumber())
                        .addValue("expiration_month", d.getCreditCard().getExpirationMonth())
                        .addValue("expiration_year", d.getCreditCard().getExpirationYear())
                        .addValue("security_code", d.getCreditCard().getSecurityCode())
                );
        d.getCreditCard().setCreditCardId(key.longValue());
    }

    private long insertDriverInUsers(Driver d) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("email", d.getDriverEmail())
                        .addValue("password", d.getDriverPassword())
                        .addValue("enabled", 1)
                        .addValue("role", "ROLE_DRIVER"));
        return key.longValue();
    }

    private void insertDriverInLinkTable(long userId, long driverId) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_driver")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("user_id", userId)
                        .addValue("driver_id", driverId));
    }

    private void insertDriverTransport(Driver d) {
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("transport")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource()
                        .addValue("driver_id", d.getDriverId())
                        .addValue("name", d.getRegisteredTransport().getName())
                        .addValue("model_name", d.getRegisteredTransport().getModelName())
                        .addValue("transport_type", d.getRegisteredTransport().getTransportType())
                        .addValue("body_type", d.getRegisteredTransport().getBodyType())
                        .addValue("number_of_seats", d.getRegisteredTransport().getNumberOfSeats())
                        .addValue("date_of_registration", d.getRegisteredTransport().getDateOfRegistration())
                        .addValue("registration_number", d.getRegisteredTransport().getRegistrationNumber())
                        .addValue("year", d.getRegisteredTransport().getYear())
                        .addValue("color", d.getRegisteredTransport().getColor())
                        .addValue("engine_id", d.getRegisteredTransport().getEngineId())
                );
        d.getCreditCard().setCreditCardId(key.longValue());
    }

    @Override
    public void updateEnabledValue(String email, int value) {
        jdbcTemplate.update("update users set enabled = ? where email = ?", value, email);
    }


}
