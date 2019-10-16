package com.loan.simulator.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.loan.simulator.model.Client;
import com.loan.simulator.model.Loan;
import com.loan.simulator.model.repository.ClientRepository;
import com.loan.simulator.model.repository.LoanRepository;

@Service
public class RiskServiceImpl implements RiskService {

    @Value("${maximum.amount}")
    private int maxAmount;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Loan verifyRiskOfLoanRequest(Client client, String ipAddress) {

        Calendar calendar = GregorianCalendar.getInstance();

        Loan loan = null;

        // can't attend for a loan > maximum permitted loan request
        if (client.getAmount() > maxAmount) {
            // null
            return loan;
        }

        if (client.getAmount() == maxAmount) {
            // verify time interval associated for the current loan request (if
            // it's 00:00->6:00 AM) => represents a risk
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

            System.out.println("Current hour: " + currentHour);
            // risk
            if (currentHour < 6) {
                System.out.println("A risk is involved: Max amount requested in the interval [00:00-6:00 AM] ");
                // null
                return loan;
            }
        }

        // verify if client reached 3 loan request from same IP in a day
        Client findExistingClient = clientRepository.findByIdentityCardNo(client.getIdentityCardNo());

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().atTime(LocalTime.MAX);

        // found existing client in db
        if (findExistingClient != null) {

            double amount = client.getAmount();
            int term = client.getTerm();

            client = findExistingClient;

            client.setAmount(amount);
            client.setTerm(term);

            // verify if we had 3 requests for the same client with same IP over the day
            long approvedLoans = loanRepository.countCustomizedClientLoans(Timestamp.valueOf(startTime),
                    Timestamp.valueOf(endTime), findExistingClient);

            System.out.println("Number of approved loans (current day) per current requested client: " + approvedLoans);

            if (approvedLoans == 3) {
                return loan;
            }
        } else {
            // save the client only if it does not exists
            clientRepository.saveAndFlush(client);
        }

        loan = new Loan();

        loan.setClient(client);
        loan.setAmount(client.getAmount());
        loan.setTerm(client.getTerm());
        loan.setIpAddress(ipAddress);
        loanRepository.save(loan);

        return loan;
    }
}
