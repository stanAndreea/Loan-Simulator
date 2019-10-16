package com.loan.simulator.model.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.loan.simulator.model.Client;
import com.loan.simulator.model.Loan;

// LoanRepository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	// count number of approved loans for a specific client - per current day
	@Query("SELECT COUNT(l) FROM Loan l WHERE l.loanRequest >= :startTime AND l.loanRequest <= :endTime AND l.client = :client")
	public long countCustomizedClientLoans(@Param("startTime") Timestamp startTime, @Param ("endTime") Timestamp endTime, @Param("client") Client client);
}
