/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import it.tss.banksystem.bank.boundary.adapters.TransactionTypeAdapter;
import it.tss.banksystem.bank.entity.Transaction;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Transient;

/**
 *
 * @author alfonso
 */
public class TransactionView {

    @JsonbTypeAdapter(TransactionTypeAdapter.class)
    public Transaction.Type type;
    @JsonbDateFormat(value = "dd/MM/yyyy")
    public LocalDateTime when;
    public double amount;
    public AccountView account;
    public AccountView transfer;
    public String descr;

    public TransactionView() {
    }

    public TransactionView(Transaction t) {
        this.type = t.getType();
        this.when = t.getCreatedOn();
        this.amount = t.getAmount();
        this.account = new AccountView(t.getAccount());
        this.transfer = t.getTransfer() == null ? null : new AccountView(t.getTransfer());

    }
}
