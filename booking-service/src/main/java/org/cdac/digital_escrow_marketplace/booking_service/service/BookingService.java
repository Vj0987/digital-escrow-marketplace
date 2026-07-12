package org.cdac.digital_escrow_marketplace.booking_service.service;

import java.util.List;

import org.cdac.digital_escrow_marketplace.booking_service.dto.BookingResponseDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.CreateBookingRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.UpdateBookingStatusRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.enums.BookingStatus;

public interface BookingService {

	public BookingResponseDTO createBooking(CreateBookingRequestDTO request, int clientId);

	public BookingResponseDTO getBookingById(long bookingId, int userId, String role);

	public List<BookingResponseDTO> getBookingsByClient(int clientId);

	public List<BookingResponseDTO> getBookingsByProvider(int providerId);

	public BookingResponseDTO updateBookingStatus(long bookingId, UpdateBookingStatusRequestDTO request,
			int providerId);

	public BookingResponseDTO cancelBooking(long bookingId, int clientId);

	public List<BookingResponseDTO> getBookingsByStatus(BookingStatus status);

	public List<BookingResponseDTO> getAllBookings();

}