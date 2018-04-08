package com.quickwolf.web.repository;

import com.quickwolf.domain.Driver;

/**
 * Created by Faust on 4/20/2017.
 */
public interface DriverRepository {
    Driver saveDriver(Driver driver);
    void updateEnabledValue(String email, int value);
}
