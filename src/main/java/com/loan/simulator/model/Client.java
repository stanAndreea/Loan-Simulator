package com.loan.simulator.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/*
 * Model class for 'Client'
 * -- contains also some constrains specific annotations
 */
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", length = 30)
    @NotBlank
    private String firstName;

    @Column(name = "SUR_NAME", length = 50)
    @NotBlank
    private String surName;

    // need to identify each client by a specific rule (for example: unique
    // identifier)
    @Column(name = "IDENTITY_CARD_NO", unique = true)
    @NotNull
    @Range(min = 10000000, max = 99999999) // at least for this demo
    private Long identityCardNo;

    @Transient
    // will not be saved in db
    @NotNull
    @Range(min = 1, max = 10000)
    private Double amount;

    @Transient
    @NotNull
    @Range(min = 1, max = 36)
    private Integer term;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID")
    private Set<Loan> loans;

    public Client() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Long getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(Long identityCardNo) {
        this.identityCardNo = identityCardNo;
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

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Client [firstName=" + firstName + ", surName=" + surName + ", identityCardNo=" + identityCardNo + "]";
    }

}
