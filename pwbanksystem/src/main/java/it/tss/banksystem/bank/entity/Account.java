/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import it.tss.banksystem.bank.boundary.dto.AccountCreate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "account")
public class Account extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "account_sequence")
    protected Long id;

    private double balance;
    @Column(name = "over_draft")
    private int overDraft;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean deleted = false;

    public Account() {
    }

    public Account(AccountCreate a, User user) {
        this.balance = a.amount;
        this.overDraft = a.overDraft;
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(int overDraft) {
        this.overDraft = overDraft;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        final Account other = (Account) obj;
        return Objects.equals(this.id, other.id);
    }

}
