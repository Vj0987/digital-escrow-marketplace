package org.cdac.digital_escrow_marketplace.booking_service.exceptions;

public class SelfBookingNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SelfBookingNotAllowedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SelfBookingNotAllowedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
