package com.quickwolf.web.form.beans;

import com.quickwolf.domain.Transport;
import com.quickwolf.domain.CreditCard;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Faust on 4/20/2017.
 */
public class RegisterDriverFormBean {
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "First name should be between 2 - 30 characters long")
    private String firstName;
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "Last name should be between 2 - 30 characters long")
    private String lastName;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Something is wrong with your driverEmail")
    private String driverEmail;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password should contain upperCase, lowerCase, number/special char and min 8 characters")
    private String driverPassword;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "0\\(\\d{2}\\)-\\d{3}-\\d{3}", message = "Here is a valid telephone number 0(69)-267-158")
    private String telephoneNumber;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "\\d{13}", message = "Look into your passport for a valid 13 digit id")
    private String humanId;
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "^([0-9]{4})-([1-9]|1[0-2])-([0-9]|1[0-9]|2[0-9]|3[0-1])$", message = "Enter the date of birth in form yyyy-mm-dd.")
    private String dateOfBirth;
    @Valid
    private CreditCard creditCard = CreditCard.newCreditCard().build();
    @Valid
    private Transport registeredTransport = Transport.newTransport().build();

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

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
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

    public void setCreditCard(List<CreditCard> creditCards) {
        this.creditCard = creditCard;
    }

    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(String humanId) {
        this.humanId = humanId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Transport getRegisteredTransport() {
        return registeredTransport;
    }

    public void setRegisteredTransport(Transport registeredTransport) {
        this.registeredTransport = registeredTransport;
    }
}
