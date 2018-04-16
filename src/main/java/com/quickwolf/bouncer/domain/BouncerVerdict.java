package com.quickwolf.bouncer.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BouncerVerdict {

    @XmlElement
    private boolean isAllowedToEnter;

    @XmlElement
    private String encryptedOrderId;

    public BouncerVerdict() {
    }

    public BouncerVerdict(boolean isAllowed) {
        this.isAllowedToEnter = isAllowed;
    }

    public BouncerVerdict(boolean isAllowed, String encryptedOrderId) {
        this.isAllowedToEnter = isAllowed;
        this.encryptedOrderId = encryptedOrderId;
    }

    public boolean isAllowedToEnter() {
        return isAllowedToEnter;
    }

    public void setIdAllowedToEnter(boolean isAllowedToEnter) {
        this.isAllowedToEnter = isAllowedToEnter;
    }

    public String getEncryptedOrderId() {
        return encryptedOrderId;
    }

    public void setEncryptedOrderId(String encryptedOrderId) {
        this.encryptedOrderId = encryptedOrderId;
    }
}
