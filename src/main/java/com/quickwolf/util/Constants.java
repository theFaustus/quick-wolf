package com.quickwolf.util;

public class Constants {
    public static final String DEFAULT_PASSENGER_ROLE = "ROLE_USER";
    public static final String DEFAULT_DRIVER_ROLE = "ROLE_DRIVER";
    public static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMIN";

    public static class Driver {
        public static final String DISABLE_ACCOUNT_TEMPLATE_NAME = "mail/notifications/suspendAccount";
        public static final String ENABLE_ACCOUNT_TEMPLATE_NAME = "mail/notifications/enableAccount";
        public static final String DISABLE_ACCOUNT_SUBJECT = "Account disabled";
        public static final String ENABLE_ACCOUNT_SUBJECT = "Account enabled";
    }

    public static class Passenger {
        public static final String DISABLE_ACCOUNT_TEMPLATE_NAME = "mail/notifications/suspendAccount";
        public static final String ENABLE_ACCOUNT_TEMPLATE_NAME = "mail/notifications/enableAccount";
    }
}
