package com.quickwolf.web.service;

import com.quickwolf.domain.Passenger;
import com.quickwolf.web.form.beans.RegisterPassengerFormBean;

/**
 * Created by Faust on 4/19/2017.
 */
public interface PassengerService {
    Passenger savePassenger(RegisterPassengerFormBean registerPassengerFormBean);
}
