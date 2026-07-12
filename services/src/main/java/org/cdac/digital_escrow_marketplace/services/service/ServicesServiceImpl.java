package org.cdac.digital_escrow_marketplace.services.service;

import java.time.LocalDateTime;
import java.util.List;

import org.cdac.digital_escrow_marketplace.services.dto.CreateServiceRequestDTO;
import org.cdac.digital_escrow_marketplace.services.dto.ServiceResponseDTO;
import org.cdac.digital_escrow_marketplace.services.dto.UpdateServiceRequestDTO;
import org.cdac.digital_escrow_marketplace.services.entity.Services;
import org.cdac.digital_escrow_marketplace.services.enums.ServiceStatus;
import org.cdac.digital_escrow_marketplace.services.exception.CategoryNotFoundException;
import org.cdac.digital_escrow_marketplace.services.exception.ServiceAlreadyDeletedException;
import org.cdac.digital_escrow_marketplace.services.exception.ServiceNotFoundException;
import org.cdac.digital_escrow_marketplace.services.exception.UnauthorizedServiceAccessException;
import org.cdac.digital_escrow_marketplace.services.mapper.ServiceMapper;
import org.cdac.digital_escrow_marketplace.services.repository.ServiceCategoryRepository;
import org.cdac.digital_escrow_marketplace.services.repository.ServicesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesServiceImpl implements ServicesService {

	@Autowired
	ServicesRepository servicesRepository;

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;

	@Override
	public ServiceResponseDTO createService(CreateServiceRequestDTO request, int providerId) {
		serviceCategoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found."));

		Services objService = new Services();
		BeanUtils.copyProperties(request, objService);
		objService.setProviderId(providerId);
		objService.setStatus(ServiceStatus.ACTIVE);
		objService.setCreatedAt(LocalDateTime.now());
		objService.setUpdatedAt(LocalDateTime.now());

		Services savedService = servicesRepository.save(objService);

		return ServiceMapper.toDTO(savedService);
	}

	@Override
	public ServiceResponseDTO updateService(long serviceId, UpdateServiceRequestDTO request, int loggedInUserId) {
		Services service = servicesRepository.findById(serviceId)
				.orElseThrow(() -> new ServiceNotFoundException("Service not found."));

		if (service.getProviderId() != loggedInUserId) {
			throw new UnauthorizedServiceAccessException("You are not authorized to update this service.");
		}

		serviceCategoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found."));

		service.setTitle(request.getTitle());
		service.setDescription(request.getDescription());
		service.setCategoryId(request.getCategoryId());
		service.setPrice(request.getPrice());
		service.setDeliveryDays(request.getDeliveryDays());
		service.setUpdatedAt(LocalDateTime.now());

		Services updatedService = servicesRepository.save(service);

		return ServiceMapper.toDTO(updatedService);
	}

	@Override
	public void deleteService(long serviceId, int LoggedInUserId) {
		Services service = servicesRepository.findById(serviceId)
				.orElseThrow(() -> new ServiceNotFoundException("Service not found."));

		if (service.getProviderId() != LoggedInUserId)
			throw new UnauthorizedServiceAccessException("You are not authorized to delete this service.");
		
		if (service.getStatus() == ServiceStatus.DELETED) 
			throw new ServiceAlreadyDeletedException("Service is already deleted.");
		

		service.setStatus(ServiceStatus.DELETED);
		service.setUpdatedAt(LocalDateTime.now());

		servicesRepository.save(service);

	}

	@Override
	public ServiceResponseDTO getServiceById(long serviceId) {

		Services service = servicesRepository.findById(serviceId)
				.orElseThrow(() -> new ServiceNotFoundException("Service not found."));
		
		if (service.getStatus() == ServiceStatus.DELETED)
			throw new ServiceNotFoundException("Service not found.");

		return ServiceMapper.toDTO(service);
	}

	@Override
	public List<ServiceResponseDTO> getAllActiveServices() {
		return servicesRepository.findByStatus(ServiceStatus.ACTIVE).stream().map(ServiceMapper::toDTO).toList();
	}

	@Override
	public List<ServiceResponseDTO> getServicesByProvider(int providerId) {

		return servicesRepository.findByProviderIdAndStatus(providerId, ServiceStatus.ACTIVE).stream()
				.map(ServiceMapper::toDTO).toList();

	}

	@Override
	public List<ServiceResponseDTO> searchServices(String keyWord) {

		return servicesRepository.findByTitleContainingIgnoreCaseAndStatus(keyWord, ServiceStatus.ACTIVE).stream()
				.map(ServiceMapper::toDTO).toList();
	}

	@Override
	public List<ServiceResponseDTO> getServicesByCategory(int categoryId) {

		return servicesRepository.findByCategoryIdAndStatus(categoryId, ServiceStatus.ACTIVE).stream()
				.map(ServiceMapper::toDTO).toList();
	}

}
