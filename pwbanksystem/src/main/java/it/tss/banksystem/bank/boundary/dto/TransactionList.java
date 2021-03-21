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
public class TransactionList {

    public long total;
    public List<TransactionView> data;

    public TransactionList() {
    }

    public TransactionList(long total, List<TransactionView> data) {
        this.total = total;
        this.data = data;
    }

}
