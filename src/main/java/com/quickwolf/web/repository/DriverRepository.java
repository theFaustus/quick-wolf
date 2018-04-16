package com.quickwolf.web.repository;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select d from Driver d where d.email = :email")
    Driver findDriverBy(@Param("email") String email);

    @Query("select d.addedTrips from Driver d where d.email = :email")
    List<Trip> findAddedTrips(@Param("email") String email);

    @Modifying
    @Query("update Driver d set d.enabled = :enabledValue where d.email = :email")
    void updateEnabledValue(@Param("email") String email, @Param("enabledValue") int value);
}
