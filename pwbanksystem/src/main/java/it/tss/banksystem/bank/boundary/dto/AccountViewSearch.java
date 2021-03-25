/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import it.tss.banksystem.bank.entity.Account;

/**
 *
 * @author alfonso
 */
public class AccountViewSearch extends AccountViewLink{
    public String lname;
    public String fname;
    public double balance;

    public AccountViewSearch() {
    }
    
    public AccountViewSearch(Account a){
        lname = a.getUser().getLname();
        fname = a.getUser().getFname();
        id = a.getId();
        balance = a.getBalance();
    }
}
