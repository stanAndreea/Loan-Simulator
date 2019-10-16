package com.loan.simulator.service;

import com.loan.simulator.model.Client;
import com.loan.simulator.model.Loan;

// Service associated Interface declaration
public interface RiskService {

	Loan verifyRiskOfLoanRequest(Client client, String ipAddress);
}
