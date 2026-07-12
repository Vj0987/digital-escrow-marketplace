package org.cdac.digital_escrow_marketplace.booking_service.exceptions;

public class BookingAlreadyCompletedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookingAlreadyCompletedException() {
		// TODO Auto-generated constructor stub
	}

	public BookingAlreadyCompletedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BookingAlreadyCompletedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public BookingAlreadyCompletedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BookingAlreadyCompletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
