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
public class AccountUpdate {

    public int overDraft;

    public AccountUpdate() {
    }

    public AccountUpdate(int overDraft) {
        this.overDraft = overDraft;
    }

}
