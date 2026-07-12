package org.cdac.digital_escrow_marketplace.services.repository;

import java.util.List;

import org.cdac.digital_escrow_marketplace.services.entity.Services;
import org.cdac.digital_escrow_marketplace.services.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    List<Services> findByStatus(ServiceStatus status);

    List<Services> findByProviderIdAndStatus(int providerId, ServiceStatus status);

    List<Services> findByCategoryIdAndStatus(int categoryId, ServiceStatus status);

    List<Services> findByTitleContainingIgnoreCaseAndStatus(String keyword, ServiceStatus status);

}