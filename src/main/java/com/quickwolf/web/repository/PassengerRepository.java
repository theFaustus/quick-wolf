package com.quickwolf.web.repository;

import com.quickwolf.domain.Passenger;
import com.quickwolf.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("select p from Passenger p where p.email = :email")
    Passenger findPassengerBy(@Param("email") String email);

    @Query("select p.bookedTrips from Passenger p where p.email = :email")
    List<Trip> findBookedTrips(@Param("email") String email);

    @Query("update Passenger p set p.enabled = :enabledValue where p.email = :email")
    @Modifying
    void updateEnabledValue(@Param("email") String email, @Param("enabledValue") int value);

    @Query("select distinct p from Passenger p join fetch p.bookedTrips where p.email = :email")
    Passenger findPassengerByEmailWithFetchedTrips(@Param("email") String email);

    @Query("select distinct p from Passenger p join fetch p.bookedTrips")
    List<Passenger> findAllPassengersWithFetchedBookedTrips();
}
