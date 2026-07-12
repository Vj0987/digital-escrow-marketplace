package org.cdac.digital_escrow_marketplace.booking_service.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);

		return new ResponseEntity<>(body, status);
	}

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<Object> handleBookingNotFound(BookingNotFoundException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<Object> handleServiceNotFound(ServiceNotFoundException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<Object> handleServiceUnavailable(ServiceUnavailableException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicatePendingBookingException.class)
	public ResponseEntity<Object> handleDuplicateBooking(DuplicatePendingBookingException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SelfBookingNotAllowedException.class)
	public ResponseEntity<Object> handleSelfBooking(SelfBookingNotAllowedException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDeliveryDurationException.class)
	public ResponseEntity<Object> handleInvalidDelivery(InvalidDeliveryDurationException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookingAlreadyCancelledException.class)
	public ResponseEntity<Object> handleCancelledBooking(BookingAlreadyCancelledException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookingAlreadyCompletedException.class)
	public ResponseEntity<Object> handleCompletedBooking(BookingAlreadyCompletedException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedBookingAccessException.class)
	public ResponseEntity<Object> handleUnauthorized(UnauthorizedBookingAccessException ex) {

		return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneral(Exception ex) {

		return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}