package com.quickwolf.web.service;

import com.quickwolf.domain.Driver;
import com.quickwolf.web.form.beans.RegisterDriverFormBean;

/**
 * Created by Faust on 4/20/2017.
 */
public interface DriverService {
    Driver saveDriver(RegisterDriverFormBean registerDriverFormBean);
}
