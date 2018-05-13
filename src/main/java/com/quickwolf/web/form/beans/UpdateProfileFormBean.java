package com.quickwolf.web.form.beans;

public class UpdateProfileFormBean {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static UpdateProfileFormBeanBuilder newBuilder() {
        return new UpdateProfileFormBeanBuilder();
    }

    public static final class UpdateProfileFormBeanBuilder {
        private UpdateProfileFormBean formBean = new UpdateProfileFormBean();

        private UpdateProfileFormBeanBuilder() {
        }

        public UpdateProfileFormBeanBuilder setFirstName(String firstName) {
            formBean.firstName = firstName;
            return this;
        }

        public UpdateProfileFormBeanBuilder setLastName(String lastName) {
            formBean.lastName = lastName;
            return this;
        }

        public UpdateProfileFormBeanBuilder setPhoneNumber(String phoneNumber) {
            formBean.phoneNumber = phoneNumber;
            return this;
        }

        public UpdateProfileFormBean build() {
            return formBean;
        }
    }
}
