package com.quickwolf.web.form.beans;

public class UpdatePasswordFormBean {
    private String oldPassword;
    private String newPassword;
    private String confirmedNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(final String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
