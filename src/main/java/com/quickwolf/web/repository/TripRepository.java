package com.quickwolf.web.repository;

import java.util.Date;
import java.util.List;

import com.quickwolf.domain.Driver;
import com.quickwolf.domain.Trip;
import com.quickwolf.web.form.beans.TripFormBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query("select t from Trip t where t.fromAddress.country = :fromCountry and t.fromAddress.city = :fromCity and " +
		"t.destinationAddress.country = :toCountry and t.destinationAddress.city = :toCity and " +
		"t.departDate = :departDate")
	List<Trip> findTripsBy(@Param("fromCountry") String fromCountry,
						   @Param("fromCity") String fromCity,
						   @Param("toCountry") String toCountry,
						   @Param("toCity") String toCity,
						   @Param("departDate") Date departDate);

}
