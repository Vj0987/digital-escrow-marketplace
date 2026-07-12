package org.cdac.digital_escrow_marketplace.services.mapper;

import org.cdac.digital_escrow_marketplace.services.dto.ServiceResponseDTO;
import org.cdac.digital_escrow_marketplace.services.entity.Services;
import org.springframework.beans.BeanUtils;

public class ServiceMapper {
	public static ServiceResponseDTO toDTO(Services service) {
		ServiceResponseDTO responseDTO = new ServiceResponseDTO();
		BeanUtils.copyProperties(service, responseDTO);
		return responseDTO;
	}
}
