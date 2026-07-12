package org.cdac.digital_escrow_marketplace.services.service;

import java.util.List;

import org.cdac.digital_escrow_marketplace.services.dto.CreateServiceRequestDTO;
import org.cdac.digital_escrow_marketplace.services.dto.ServiceResponseDTO;
import org.cdac.digital_escrow_marketplace.services.dto.UpdateServiceRequestDTO;

public interface ServicesService {
	public ServiceResponseDTO createService(CreateServiceRequestDTO request, int providerId);

	public ServiceResponseDTO updateService(long serviceId, UpdateServiceRequestDTO request, int loggedInUserI);

	public void deleteService(long serviceId, int LoggedInUserId);

	public ServiceResponseDTO getServiceById(long serviceId);

	public List<ServiceResponseDTO> getAllActiveServices();

	public List<ServiceResponseDTO> getServicesByProvider(int providerId);

	public List<ServiceResponseDTO> searchServices(String keyWord);

	public List<ServiceResponseDTO> getServicesByCategory(int categoryId);

}
