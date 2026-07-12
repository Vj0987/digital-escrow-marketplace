package org.cdac.digital_escrow_marketplace.booking_service.controllers;

import java.util.List;

import org.cdac.digital_escrow_marketplace.booking_service.dto.BookingResponseDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.CreateBookingRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.dto.UpdateBookingStatusRequestDTO;
import org.cdac.digital_escrow_marketplace.booking_service.enums.BookingStatus;
import org.cdac.digital_escrow_marketplace.booking_service.security.CustomUserPrincipal;
import org.cdac.digital_escrow_marketplace.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PreAuthorize("hasRole('CLIENT')")
	@PostMapping
	public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody CreateBookingRequestDTO request,
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(bookingService.createBooking(request, principal.getUserId()));
	}

	@PreAuthorize("hasAnyRole('CLIENT','PROVIDER','ADMIN')")
	@GetMapping("/{bookingId}")
	public ResponseEntity<BookingResponseDTO> getBookingById(
	        @PathVariable Long bookingId,
	        @AuthenticationPrincipal CustomUserPrincipal principal) {

	    return ResponseEntity.ok(
	            bookingService.getBookingById(
	                    bookingId,
	                    principal.getUserId(),
	                    principal.getRole()));
	}

	@PreAuthorize("hasRole('CLIENT')")
	@GetMapping("/client")
	public ResponseEntity<List<BookingResponseDTO>> getBookingsByClient(
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		return ResponseEntity.ok(bookingService.getBookingsByClient(principal.getUserId()));
	}

	@PreAuthorize("hasRole('PROVIDER')")
	@GetMapping("/provider")
	public ResponseEntity<List<BookingResponseDTO>> getBookingsByProvider(
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		return ResponseEntity.ok(bookingService.getBookingsByProvider(principal.getUserId()));
	}

	@PreAuthorize("hasRole('PROVIDER')")
	@PutMapping("/{bookingId}/status")
	public ResponseEntity<BookingResponseDTO> updateBookingStatus(@PathVariable long bookingId,
			@Valid @RequestBody UpdateBookingStatusRequestDTO request,
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, request, principal.getUserId()));
	}

	@PreAuthorize("hasRole('CLIENT')")
	@PutMapping("/{bookingId}/cancel")
	public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable long bookingId,
			@AuthenticationPrincipal CustomUserPrincipal principal) {
		return ResponseEntity.ok(bookingService.cancelBooking(bookingId, principal.getUserId()));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/bookingStatus/{status}")
	public ResponseEntity<List<BookingResponseDTO>> getBookingsByStatus(@PathVariable BookingStatus status) {
		return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}

}
