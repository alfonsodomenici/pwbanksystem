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
public class AccountViewLink {

    public long id;

    public AccountViewLink() {
    }

    public AccountViewLink(Account a) {
        this.id = a.getId();
    }

}
