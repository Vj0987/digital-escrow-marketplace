package org.cdac.digital_escrow_marketplace.booking_service.mapper;

import org.cdac.digital_escrow_marketplace.booking_service.dto.BookingResponseDTO;
import org.cdac.digital_escrow_marketplace.booking_service.entity.Booking;
import org.springframework.beans.BeanUtils;

public class BookingMapper {

	private BookingMapper() {
		// Prevent instantiation
	}

	public static BookingResponseDTO toBookingResponseDTO(Booking booking) {

		if (booking == null) {
			return null;
		}

		BookingResponseDTO response = new BookingResponseDTO();
		BeanUtils.copyProperties(booking, response);

		return response;
	}

}