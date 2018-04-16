package com.quickwolf.bouncer.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TripAttendRequest {

    private String encryptedOrderId;

    public String getEncryptedOrderId() {
        return encryptedOrderId;
    }

    public void setEncryptedOrderId(String encryptedOrderId) {
        this.encryptedOrderId = encryptedOrderId;
    }
}
