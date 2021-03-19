/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import it.tss.banksystem.bank.boundary.AccountLinkAdapter;
import java.io.Serializable;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.tss.banksystem.bank.boundary.TransactionTypeAdapter;
import javax.persistence.Transient;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractEntity implements Serializable {

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    @JsonbTypeAdapter(TransactionTypeAdapter.class)
    @Enumerated(EnumType.STRING)
    private Type type;

    private Double amount;

    @JsonbTypeAdapter(AccountLinkAdapter.class)
    @ManyToOne(optional = false)
    private Account account;

    @JsonbTypeAdapter(AccountLinkAdapter.class)
    @ManyToOne
    private Account transfer;

    @Column(length = 2048)
    private String note;

    @Transient
    private String descr = "EFFETTUATO";
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getTransfer() {
        return transfer;
    }

    public void setTransfer(Account transfer) {
        this.transfer = transfer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    
}
