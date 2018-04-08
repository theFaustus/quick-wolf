package com.quickwolf.web.form.beans;

import com.quickwolf.domain.CreditCard;
import com.quickwolf.domain.Trip;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Faust on 4/19/2017.
 */
public class RegisterPassengerFormBean {

    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "First name should be between 2 - 30 characters long")
    private String firstName;
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "Last name should be between 2 - 30 characters long")
    private String lastName;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Something is wrong with your email")
    private String passengerEmail;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password should contain upperCase, lowerCase, number/special char and min 8 characters")
    private String passengerPassword;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "0\\(\\d{2}\\)-\\d{3}-\\d{3}", message = "Here is a valid telephone number 0(69)-267-158")
    private String telephoneNumber;
    @Valid
    private CreditCard creditCard = CreditCard.newCreditCard().build();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerPassword() {
        return passengerPassword;
    }

    public void setPassengerPassword(String passengerPassword) {
        this.passengerPassword = passengerPassword;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
