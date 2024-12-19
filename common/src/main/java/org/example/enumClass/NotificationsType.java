package org.example.enumClass;

public enum NotificationsType {
    BOOKING_CONFIRMED,
    BOOKING_CANCELED,
    BOOKING_EXTENDED,
    BOOKING_ENDING_SOON,
    BOOKING_TIME_EXPIRED,
    BOOKING_ERROR;

    public static NotificationsType fromNotificationsName(String name) {
        for (NotificationsType type : NotificationsType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid notification type: " + name);
    }
}
