package org.cdac.digital_escrow_marketplace.booking_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.cdac.digital_escrow_marketplace.booking_service.client.ServiceClient;
import org.cdac.digital_escrow_marketplace.booking_service.dto.BookingResponseDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.CreateBookingRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.ServiceResponseDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.UpdateBookingStatusRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.entity.Booking;
import org.cdac.digital_escrow_marketplace.booking_service.enums.BookingStatus;
import org.cdac.digital_escrow_marketplace.booking_service.enums.ServiceStatus;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.BookingAlreadyCancelledException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.BookingAlreadyCompletedException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.BookingNotFoundException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.DuplicatePendingBookingException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.InvalidDeliveryDurationException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.SelfBookingNotAllowedException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.ServiceNotFoundException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.ServiceUnavailableException;
import org.cdac.digital_escrow_marketplace.booking_service.exceptions.UnauthorizedBookingAccessException;
import org.cdac.digital_escrow_marketplace.booking_service.mapper.BookingMapper;
import org.cdac.digital_escrow_marketplace.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feign.FeignException;
import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	ServiceClient serviceClient;

	@Transactional
	@Override
	public BookingResponseDTO createBooking(CreateBookingRequestDTO request, int clientId) {
		LocalDateTime now = LocalDateTime.now();
		ServiceResponseDTO serviceResponse = null;

		if (bookingRepository.findByClientIdAndServiceIdAndBookingStatus(clientId, request.getServiceId(),
				BookingStatus.PENDING_PAYMENT).isPresent()) {

			throw new DuplicatePendingBookingException("A pending booking already exists for this service.");
		}

		try {
			serviceResponse = serviceClient.getServiceById(request.getServiceId());
		} catch (FeignException.NotFound e) {
			throw new ServiceNotFoundException("Service not found");
		} catch (FeignException e) {
			e.printStackTrace();
			throw e;
		}

		if (serviceResponse.getStatus() != ServiceStatus.ACTIVE) {
			throw new ServiceUnavailableException("Service is not available");
		}
		if (clientId == serviceResponse.getProviderId()) {
			throw new SelfBookingNotAllowedException("You cannot book your own service");
		}

		if (serviceResponse.getDeliveryDays() <= 0) {
			throw new InvalidDeliveryDurationException("Invalid delivery duration");
		}

		Booking booking = new Booking();
		booking.setServiceId(request.getServiceId());
		booking.setProviderId(serviceResponse.getProviderId());
		booking.setClientId(clientId);
		booking.setAmount(serviceResponse.getPrice());
		booking.setBookingStatus(BookingStatus.PENDING_PAYMENT);
		booking.setBookingDate(now);
		booking.setDeliveryDate(LocalDate.now().plusDays(serviceResponse.getDeliveryDays()));
		booking.setCreatedAt(now);
		booking.setUpdatedAt(now);

		Booking createdBooking = bookingRepository.save(booking);
		return BookingMapper.toBookingResponseDTO(createdBooking);
	}

	@Override
	public BookingResponseDTO getBookingById(long bookingId, int userId, String role) {

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));

		if (!"ADMIN".equalsIgnoreCase(role)) {

			if (!(booking.getClientId() == userId) && !(booking.getProviderId() == userId)) {
				throw new UnauthorizedBookingAccessException("You are not authorized to access this booking");
			}
		}

		return BookingMapper.toBookingResponseDTO(booking);
	}

	@Override
	public List<BookingResponseDTO> getBookingsByClient(int clientId) {

		return bookingRepository.findByClientId(clientId).stream().map(BookingMapper::toBookingResponseDTO).toList();
	}

	@Override
	public List<BookingResponseDTO> getBookingsByProvider(int providerId) {

		return bookingRepository.findByProviderId(providerId).stream().map(BookingMapper::toBookingResponseDTO)
				.toList();

	}

	@Transactional
	@Override
	public BookingResponseDTO updateBookingStatus(long bookingId, UpdateBookingStatusRequestDTO request,
			int providerId) {

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));

		if (booking.getProviderId() != providerId)
			throw new UnauthorizedBookingAccessException("You are not authorized to update this booking");

		if (booking.getBookingStatus() == BookingStatus.CANCELLED)
			throw new BookingAlreadyCancelledException("Cancelled booking cannot be updated");

		if (booking.getBookingStatus() == BookingStatus.COMPLETED)
			throw new BookingAlreadyCompletedException("Completed booking cannot be updated");

		BookingStatus current = booking.getBookingStatus();
		BookingStatus newStatus = request.getBookingStatus();

		switch (current) {

		case CONFIRMED:

			if (newStatus != BookingStatus.IN_PROGRESS)
				throw new IllegalArgumentException("Booking can only move from CONFIRMED to IN_PROGRESS");
			break;

		case IN_PROGRESS:

			if (newStatus != BookingStatus.COMPLETED)
				throw new IllegalArgumentException("Booking can only move from IN_PROGRESS to COMPLETED");
			break;

		default:
			throw new IllegalArgumentException("Invalid booking status transition");
		}

		booking.setBookingStatus(newStatus);
		booking.setUpdatedAt(LocalDateTime.now());

		Booking updatedBooking = bookingRepository.save(booking);

		return BookingMapper.toBookingResponseDTO(updatedBooking);
	}

	@Transactional
	@Override
	public BookingResponseDTO cancelBooking(long bookingId, int clientId) {

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking Not found"));

		if (booking.getClientId() != clientId)
			throw new UnauthorizedBookingAccessException("You are not authorized to cancel this booking");

		if (booking.getBookingStatus() == BookingStatus.CANCELLED)
			throw new BookingAlreadyCancelledException("Booking is already cancelled");

		if (booking.getBookingStatus() == BookingStatus.COMPLETED)
			throw new BookingAlreadyCompletedException("Completed booking cannot be cancelled");

		booking.setBookingStatus(BookingStatus.CANCELLED);
		booking.setUpdatedAt(LocalDateTime.now());

		Booking cancelledBooking = bookingRepository.save(booking);

		return BookingMapper.toBookingResponseDTO(cancelledBooking);
	}

	@Override
	public List<BookingResponseDTO> getBookingsByStatus(BookingStatus status) {

		return bookingRepository.findByBookingStatus(status).stream().map(BookingMapper::toBookingResponseDTO).toList();
	}

	@Override
	public List<BookingResponseDTO> getAllBookings() {

		return bookingRepository.findAll().stream().map(BookingMapper::toBookingResponseDTO).toList();
	}

}
