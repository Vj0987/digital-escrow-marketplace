package org.cdac.digital_escrow_marketplace.users.repository;

import java.util.Optional;

import org.cdac.digital_escrow_marketplace.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	public Optional<Users> findByEmail(String email);
}
