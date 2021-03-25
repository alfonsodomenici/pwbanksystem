/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import java.util.List;

/**
 *
 * @author alfonso
 */
public class AccountList {

    public long total;
    public List<AccountViewLink> data;

    public AccountList() {
    }

    public AccountList(long total, List<AccountViewLink> data) {
        this.total = total;
        this.data = data;
    }

}
