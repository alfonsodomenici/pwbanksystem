/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "account")
public class Account extends AbstractEntity implements Serializable{

    private Double balance;
    @Column(name = "over_draft")
    private Long overDraft;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(Double balance, Long overDraft, User user) {
        this.balance = balance;
        this.overDraft = overDraft;
        this.user = user;
    }
    
    
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(Long overDraft) {
        this.overDraft = overDraft;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
