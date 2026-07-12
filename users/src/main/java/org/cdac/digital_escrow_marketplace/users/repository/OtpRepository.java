package org.cdac.digital_escrow_marketplace.users.repository;

import java.util.Optional;

import org.cdac.digital_escrow_marketplace.users.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {
	public Optional<Otp> findByEmail(String email);
}
