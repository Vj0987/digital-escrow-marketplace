package org.cdac.digital_escrow_marketplace.booking_service.repository;

import java.util.List;
import java.util.Optional;

import org.cdac.digital_escrow_marketplace.booking_service.entity.Booking;
import org.cdac.digital_escrow_marketplace.booking_service.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	public Optional<Booking> findByClientIdAndServiceIdAndBookingStatus(Integer clientId, Long serviceId,
			BookingStatus bookingStatus);

	public List<Booking> findByBookingStatus(BookingStatus status);

	public List<Booking> findByClientId(Integer clientId);

	public List<Booking> findByProviderId(Integer providerId);
}
