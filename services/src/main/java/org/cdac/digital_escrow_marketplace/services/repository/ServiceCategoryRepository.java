package org.cdac.digital_escrow_marketplace.services.repository;

import java.util.Optional;

import org.cdac.digital_escrow_marketplace.services.entity.ServiceCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategories, Integer> {

	public Optional<ServiceCategories> findByCategoryName(String categoryname);
}
