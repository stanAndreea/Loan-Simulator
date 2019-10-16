package com.loan.simulator.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LOAN")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)

    // don't want to expose field in response
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JsonIgnore
    @NotNull
    private Client client;

    @Column(name = "AMOUNT")
    @NotNull
    private Double amount;

    @Column(name = "TERM")
    @NotNull
    private Integer term;

    @Column(name = "LOAN_REQUEST")
    @CreationTimestamp
    @Type(type="java.sql.Timestamp")
    @JsonIgnore
    private Timestamp loanRequest;

    @Column(name = "IP_ADDRESS")
    @NotBlank
    private String ipAddress;

    public Loan() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Timestamp getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(Timestamp loanRequest) {
        this.loanRequest = loanRequest;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
