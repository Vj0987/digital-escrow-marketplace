package org.cdac.digital_escrow_marketplace.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = { "org.cdac.digital_escrow_marketplace.controllers",
		"org.cdac.digital_escrow_marketplace.users.services", "org.cdac.digital_escrow_marketplace.users.security",
		"org.cdac.digital_escrow_marketplace.config" })
@EntityScan(basePackages = { "org.cdac.digital_escrow_marketplace.users.entity" })
@EnableJpaRepositories(basePackages = { "org.cdac.digital_escrow_marketplace.repository" })
@EnableWebSecurity
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
