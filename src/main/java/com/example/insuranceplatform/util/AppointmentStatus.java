package com.example.insuranceplatform.util;

public enum AppointmentStatus {
    PENDING,       // booked, awaiting doctor confirmation
    CONFIRMED,     // doctor confirmed
    COMPLETED,     // appointment took place
    CANCELLED,     // cancelled by patient or doctor
    NO_SHOW
}
