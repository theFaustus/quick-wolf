package com.quickwolf.web.repository;

import com.quickwolf.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Faust on 4/16/2018.
 */
public interface CountryRepository extends JpaRepository<Country, String>{
    Country findByValue(String value);
}
