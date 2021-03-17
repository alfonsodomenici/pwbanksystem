/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    
    
}
