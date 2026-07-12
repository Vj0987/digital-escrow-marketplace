package org.cdac.digital_escrow_marketplace.booking_service.client;

import org.cdac.digital_escrow_marketplace.booking_service.config.FeignConfig;
import org.cdac.digital_escrow_marketplace.booking_service.dto.ServiceResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "SERVICES-SERVICE",
        configuration = FeignConfig.class)
public interface ServiceClient {

	@GetMapping("/services/{serviceId}")
	public ServiceResponseDTO getServiceById(@PathVariable Long serviceId);
}
