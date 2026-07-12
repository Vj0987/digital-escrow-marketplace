package org.cdac.digital_escrow_marketplace.services.controllers;

import java.util.List;

import org.cdac.digital_escrow_marketplace.services.dto.CreateServiceRequestDTO;
import org.cdac.digital_escrow_marketplace.services.dto.ServiceResponseDTO;
import org.cdac.digital_escrow_marketplace.services.dto.UpdateServiceRequestDTO;
import org.cdac.digital_escrow_marketplace.services.security.CustomUserPrincipal;
import org.cdac.digital_escrow_marketplace.services.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

	@Autowired
	ServicesService servicesService;
	
	@PreAuthorize("hasRole('PROVIDER')")
	@PostMapping
	public ResponseEntity<ServiceResponseDTO> createService(@RequestBody CreateServiceRequestDTO request,
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		int providerId = principal.getUserId();

		ServiceResponseDTO response = servicesService.createService(request, providerId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PreAuthorize("hasRole('PROVIDER')")
	@PutMapping("/{serviceId}")
	public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable long serviceId,
			@RequestBody UpdateServiceRequestDTO request, @AuthenticationPrincipal CustomUserPrincipal principal) {

		ServiceResponseDTO response = servicesService.updateService(serviceId, request, principal.getUserId());

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('PROVIDER')")
	@DeleteMapping("/{serviceId}")
	public ResponseEntity<Void> deleteService(@PathVariable long serviceId,
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		servicesService.deleteService(serviceId, principal.getUserId());

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{serviceId}")
	public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable long serviceId) {
		ServiceResponseDTO responseDTO = servicesService.getServiceById(serviceId);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping
	public ResponseEntity<List<ServiceResponseDTO>> getAllActiveService() {
		List<ServiceResponseDTO> responseDTOList = servicesService.getAllActiveServices();
		return ResponseEntity.ok(responseDTOList);
	}

	@PreAuthorize("hasRole('PROVIDER')")
	@GetMapping("/my-services")
	public ResponseEntity<List<ServiceResponseDTO>> getMyServices(
			@AuthenticationPrincipal CustomUserPrincipal principal) {

		List<ServiceResponseDTO> response = servicesService.getServicesByProvider(principal.getUserId());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<List<ServiceResponseDTO>> searchServices(@RequestParam String keyWord) {
		List<ServiceResponseDTO> responseDTOList = servicesService.searchServices(keyWord);
		return ResponseEntity.ok(responseDTOList);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ServiceResponseDTO>> getServicesByCategory(@PathVariable int categoryId) {

		return ResponseEntity.ok(servicesService.getServicesByCategory(categoryId));
	}
}
