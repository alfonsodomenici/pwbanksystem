/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

/**
 *
 * @author alfonso
 */
public class AccountCreate {
    public double amount;
    public int overDraft;

    public AccountCreate() {
    }

    public AccountCreate(double amount, int overDraft) {
        this.amount = amount;
        this.overDraft = overDraft;
    }
    
    
}
