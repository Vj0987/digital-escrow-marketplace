package org.cdac.digital_escrow_marketplace.booking_service.dto;

import java.math.BigDecimal;

import org.cdac.digital_escrow_marketplace.booking_service.enums.ServiceStatus;

public class ServiceResponseDTO {

	private Long serviceId;

	private Integer providerId;

	private BigDecimal price;

	private String title;

	private Integer deliveryDays;

	private ServiceStatus status;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(Integer deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

}
