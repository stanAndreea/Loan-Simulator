package com.loan.simulator.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loan.simulator.model.Client;
import com.loan.simulator.model.Loan;
import com.loan.simulator.service.RiskService;

// main controller class that will inject the riskService
@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private RiskService riskService;

    private static final String REJECTION_MESSAGE = "rejection";

    @RequestMapping(value = "/apply", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> applyForLoan(@Valid @RequestBody Client client, Errors errors,
            HttpServletRequest request) {

        if (errors.hasErrors()) {
            String error = "";

            // optional... display the error message in logs
            for (Object object : errors.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    error = fieldError.getDefaultMessage();
                }

                if (object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    error = objectError.getDefaultMessage();
                }

                System.err.println("Bad request..." + error);
            }

            return new ResponseEntity<String>(REJECTION_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        String requestIpAddress = request.getRemoteAddr();

        Loan loan = riskService.verifyRiskOfLoanRequest(client, requestIpAddress);

        if (loan == null) {
            return new ResponseEntity<String>("rejection", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Loan>(loan, HttpStatus.CREATED);
    }
}
