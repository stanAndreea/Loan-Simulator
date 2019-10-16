package com.loan.simulator.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.simulator.model.Client;

/*
 * Repository associated to 'Client' class
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	// find the client by an 'id card' (unique)
	Client findByIdentityCardNo(long identityCardNo);
}
