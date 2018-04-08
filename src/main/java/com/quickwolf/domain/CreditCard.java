package com.quickwolf.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Faust on 4/19/2017.
 */
@Embeddable
public class CreditCard {
    @Column(name = "cc_first_name")
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "First name should be between 2 - 30 characters long")
    private String firstName;

    @Column(name = "cc_last_name")
    @NotNull(message = "You have to fill this element")
    @Size(min = 2, max = 30, message = "Last name should be between 2 - 30 characters long")
    private String lastName;

    @Column(name = "cc_number")
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11}",message = "Enter please a valid credit card, e support : Visa, Mastercard, Amex, Discover.")
    private String cardNumber;

    @Column(name = "cc_expiration_month")
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "(0[1-9]|1[0-2])",message = "Enter please a valid expiration month.")
    private String expirationMonth;

    @Column(name = "cc_expiration_year")
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "[0-9]{2}",message = "Enter please a valid expiration year.")
    private String expirationYear;

    @Column(name = "cc_security_code")
    @NotNull(message = "You have to fill this element")
    @Pattern(regexp = "[0-9]{3,4}",message = "Enter please a valid cvv.")
    private String securityCode;


    public CreditCard() {
    }

    private CreditCard(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.cardNumber = builder.cardNumber;
        this.expirationMonth = builder.expirationMonth;
        this.expirationYear = builder.expirationYear;
        this.securityCode = builder.securityCode;
    }

    public static Builder newCreditCard() {
        return new Builder();
    }


    public static final class Builder {
        private String firstName;
        private String lastName;
        private String cardNumber;
        private String expirationMonth;
        private String expirationYear;
        private String securityCode;
        private long creditCardId;

        private Builder() {
        }

        public CreditCard build() {
            return new CreditCard(this);
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder expirationMonth(String expirationMonth) {
            this.expirationMonth = expirationMonth;
            return this;
        }

        public Builder expirationYear(String expirationYear) {
            this.expirationYear = expirationYear;
            return this;
        }

        public Builder securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public Builder creditCardId(long securityCode) {
            this.creditCardId = creditCardId;
            return this;
        }
    }

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }


    @Override
    public String toString() {
        return "CreditCard{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", securityCode='" + securityCode + '\'' +
                '}';
    }
}
