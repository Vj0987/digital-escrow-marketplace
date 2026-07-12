package org.cdac.digital_escrow_marketplace.booking_service.dto;

import org.cdac.digital_escrow_marketplace.booking_service.enums.BookingStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateBookingStatusRequestDTO {

    @NotNull(message = "Booking status is required")
    private BookingStatus bookingStatus;

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}