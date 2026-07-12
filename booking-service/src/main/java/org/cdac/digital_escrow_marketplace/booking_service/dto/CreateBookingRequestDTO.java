package org.cdac.digital_escrow_marketplace.booking_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateBookingRequestDTO {

    @Positive(message = "Service ID must be greater than 0")
    private long serviceId;

    @NotNull(message = "Delivery date is required")
    @Future(message = "Delivery date must be a future date")
    private LocalDate deliveryDate;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}