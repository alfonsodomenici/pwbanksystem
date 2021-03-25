/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.tss.banksystem.bank.boundary.dto.TransactionCreate;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "trans_sequence", sequenceName = "trans_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "trans_sequence")
    protected Long id;

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    private Double amount;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne
    private Account transfer;

    @Column(length = 2048)
    private String note;

    public Transaction() {
    }

    public Transaction(TransactionCreate t, Account account, Account transfer) {
        this.type = t.type;
        this.amount = t.amount;
        this.account = account;
        this.transfer = transfer;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        return Objects.equals(this.id, other.id);
    }

}
